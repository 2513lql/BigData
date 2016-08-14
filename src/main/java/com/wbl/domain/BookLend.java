package com.wbl.domain;

import java.util.Date;

/**
 * Created with Simple_love
 * Date: 2016/5/4.
 * Time: 10:13
 */
public class BookLend {
        private String readerId;
        private String readerName;
        private String bookName;
        private Date lentTime;
        private Date lendDeadLIne;

        public BookLend() {
        }

        public String getReaderId() {
                return readerId;
        }

        public void setReaderId(String readerId) {
                this.readerId = readerId;
        }

        public String getReaderName() {
                return readerName;
        }

        public void setReaderName(String readerName) {
                this.readerName = readerName;
        }

        public String getBookName() {
                return bookName;
        }

        public void setBookName(String bookName) {
                this.bookName = bookName;
        }

        public Date getLentTime() {
                return lentTime;
        }

        public void setLentTime(Date lentTime) {
                this.lentTime = lentTime;
        }

        public Date getLendDeadLIne() {
                return lendDeadLIne;
        }

        public void setLendDeadLIne(Date lendDeadLIne) {
                this.lendDeadLIne = lendDeadLIne;
        }

        @Override
        public String toString() {
                return "BookLend{" +
                        "readerId='" + readerId + '\'' +
                        ", readerName='" + readerName + '\'' +
                        ", bookName='" + bookName + '\'' +
                        '}';
        }
}
