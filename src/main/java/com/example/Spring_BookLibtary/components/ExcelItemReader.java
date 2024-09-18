package com.example.Spring_BookLibtary.components;

import com.example.Spring_BookLibtary.models.Book;
import com.example.Spring_BookLibtary.models.User;
import com.example.Spring_BookLibtary.roles.Genre;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.batch.item.*;
import org.springframework.core.io.Resource;

import java.io.InputStream;
import java.util.Random;

public class ExcelItemReader implements ItemReader<Book>, ResourceAware {

    private Resource resource;
    private Workbook workbook;
    private Sheet sheet;
    private int currentRow = 1;
    private User user;



    @Override
    public Book read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (sheet == null) {
            InputStream inputStream = resource.getInputStream();
            workbook = WorkbookFactory.create(inputStream);
            sheet = workbook.getSheetAt(0);
        }

        int i = sheet.getLastRowNum();
        if (currentRow > i) {
            return null;
        }

        Row row = sheet.getRow(currentRow++);
        if (row == null) {
            return null;
        }

        Book book = new Book();
        book.setName(row.getCell(0).getStringCellValue());
        book.setGenre(Genre.valueOf(row.getCell(1).getStringCellValue()));
        book.setUser(user);
        book.setDate(String.valueOf(row.getCell(2).getNumericCellValue()));
        book.setCost((int) row.getCell(3).getNumericCellValue());
        book.setCount((long) row.getCell(4).getNumericCellValue());


        return book;
    }

    @Override
    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
