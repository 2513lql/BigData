package com.wbl.modal.Enum;

/**
 * Created with Simple_love
 * Date: 2016/5/3.
 * Time: 19:59
 */
public enum ResultType {
        SUCCESS("SUCCESS"),ERROR("ERROR"),RESULT("RESULT");
        private final String text;
        private ResultType(String text){
                this.text = text;
        }

        @Override
        public String toString() {
                return this.text;
        }

        public boolean equals(String text){
                return this.text.equals(text);
        }
}
