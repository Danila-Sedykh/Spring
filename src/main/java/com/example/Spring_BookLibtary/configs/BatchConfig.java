package com.example.Spring_BookLibtary.configs;

import com.example.Spring_BookLibtary.components.BookItemProcessor;
import com.example.Spring_BookLibtary.components.ExcelItemReader;
import com.example.Spring_BookLibtary.components.FileCleanupListener;
import com.example.Spring_BookLibtary.models.Book;
import com.example.Spring_BookLibtary.repository.UserRepository;
import com.example.Spring_BookLibtary.security.JwtUtil;
import com.example.Spring_BookLibtary.service.UserService;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Bean
    public Job job() {
        return new JobBuilder("bookJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(new FileCleanupListener())
                .flow(step1())
                .end()
                .build();
    }

    @Bean
    public Step step1() {
        return new StepBuilder("step1", jobRepository)
                .<Book, Book>chunk(10, transactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .listener(stepExecutionListener())
                .taskExecutor(taskExecutor())
                .build();
    }


    @Bean
    public ItemReader<Book> reader() {
        return new ExcelItemReader();
    }

    @Bean
    public ItemProcessor<Book, Book> processor() {
        return new BookItemProcessor();
    }

    @Bean
    public ItemWriter<Book> writer() {
        return new JdbcBatchItemWriterBuilder<Book>()
                .dataSource(dataSource)
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Book>() {
                    @Override
                    public SqlParameterSource createSqlParameterSource(Book item) {
                        MapSqlParameterSource parameters = new MapSqlParameterSource();
                        parameters.addValue("name", item.getName());
                        parameters.addValue("genre", item.getGenre() != null ? item.getGenre().name() : null);
                        parameters.addValue("user", item.getUser() != null ? item.getUser().getId() : null);
                        parameters.addValue("date", item.getDate());
                        parameters.addValue("cost", item.getCost() != null ? item.getCost() : null);
                        parameters.addValue("count", item.getCount());
                        return parameters;
                    }
                })
                .sql("INSERT INTO books ( book_name, genre, production_date, user_id, book_cost, book_count)  "+
                        "VALUES ( :name, :genre, :date, :user, :cost, :count)")
                .build();
    }

    @Bean
    public StepExecutionListener stepExecutionListener() {
        return new StepExecutionListener() {
            @Override
            public void beforeStep(StepExecution stepExecution) {
                JobParameters jobParameters = stepExecution.getJobParameters();
                String filePath = jobParameters.getString("filePath");
                String token = jobParameters.getString("token");


                ExcelItemReader excelItemReader = (ExcelItemReader) reader();
                excelItemReader.setResource(new FileSystemResource(filePath));
                excelItemReader.setUser(userService.getUserFromToken(token));

            }

            @Override
            public ExitStatus afterStep(StepExecution stepExecution) {
                return ExitStatus.COMPLETED;
            }
        };
    }

    @Bean
    public TaskExecutor taskExecutor() {
        SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor();
        taskExecutor.setConcurrencyLimit(3);
        return taskExecutor;
    }
}
