package com.wbl.controller;

import com.wbl.aop.ProvAnnotation;
import com.wbl.service.OuterDataService;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.wbl.modal.Enum.Activity.AGGREGATION;
import static com.wbl.modal.Enum.Activity.EXPORT;
import static com.wbl.modal.Enum.Activity.IMPORT;

/**
 * Created with Simple_love
 * Date: 2016/5/4.
 * Time: 10:36
 */
@Controller
@RequestMapping("/operate")
public class DataOperateController {
        private Logger logger = Logger.getLogger(DataOperateController.class);

        private JSONObject result = new JSONObject();

        @Autowired
        private OuterDataService outerDataService;

        @RequestMapping("/exportTable")
        @ResponseBody
        @ProvAnnotation(EXPORT)
        public JSONObject exportTable(@RequestParam("fileName")String fileName,
                                      @RequestParam("dataName")String dataName,@RequestParam("userName")String userName) throws Exception{
                return outerDataService.exportTable(fileName,dataName,userName);
        }

        @RequestMapping("/aggregation")
        @ResponseBody
        @ProvAnnotation(AGGREGATION)
        public JSONObject aggregation(@RequestParam("fileName")String fileName,
                                      @RequestParam("userName")String userName) throws Exception{
                return outerDataService.aggregation(fileName,userName);
        }

        @RequestMapping("/export")
        @ResponseBody
        @ProvAnnotation(EXPORT)
        public JSONObject exportData(@RequestParam("userName")String userName,
                                     @RequestParam("dataName")String dataName){
                return outerDataService.exportData(dataName);
        }

        @RequestMapping("/import")
        @ResponseBody
        @ProvAnnotation(IMPORT)
        public JSONObject importData(@RequestParam("url")String url,
                                     @RequestParam("srcName")String srcName,@RequestParam("destName")String destName,
                                     @RequestParam("userName")String userName){
                return outerDataService.importData(url, srcName, destName, userName);
        }

}
