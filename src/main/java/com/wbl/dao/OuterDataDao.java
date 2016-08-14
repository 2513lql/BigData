package com.wbl.dao;

import com.wbl.domain.BookLend;
import com.wbl.domain.Student;
import com.wbl.domain.StudentBook;

import java.util.List;

/**
 * Created with Simple_love
 * Date: 2016/5/4.
 * Time: 10:14
 */
public interface OuterDataDao {
        List<String> getReaderName();
        List<Student> getStudentBasicInfo();
        List<BookLend> getBookLendInfo();

        List<StudentBook> getStudentBookLend();

        List<Student> getStudent();
        List<BookLend> getBookLend();
}
