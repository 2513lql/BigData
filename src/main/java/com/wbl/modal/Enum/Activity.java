package com.wbl.modal.Enum;

/**
 * Created with Simple_love
 * Date: 2016/5/3.
 * Time: 19:56
 */
public enum Activity {
        DOWNLOAD("DOWNLOAD"),UPLOAD("UPLOAD"),QUERY("QUERY"),LOGIN("LOGIN"),
        AGGREGATION("AGGREGATION"),IMPORT("IMPORT"),EXPORT("EXPORT"),SPLIT("SPLIT");
        private String name;
        private Activity(String name){
                this.name = name;
        }
        public String toString(){
                return this.name();
        }
}
