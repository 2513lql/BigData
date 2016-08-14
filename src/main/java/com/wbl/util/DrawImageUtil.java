package com.wbl.util;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created with Simple_love
 * Date: 2016/5/3.
 * Time: 19:50
 */
public class DrawImageUtil {
        private static final String CHAR_FOR_JOIN_NODE = "->";
        private static final String NEWLINE = "\n";
        private static final String IMAGE_TYPE = "svg";


        public static void draw(List<String> relations,String fileName){
                File out = new File(getPath(fileName));
                draw(relations,out);
        }

        public static byte[] draw(String dotString){
                File out = null;
                GraphvizUtil gv = new GraphvizUtil();
                try {
                        out = File.createTempFile("temp",".svg");
                        gv.add(gv.start_graph());
                        gv.add(dotString);
                        gv.add(gv.end_graph());
                        gv.writeGraphToFile(gv.getGraph(gv.getDotSource(),IMAGE_TYPE),out);
                        return IOUtils.toByteArray(new FileInputStream(out));
                } catch (IOException e) {
                        e.printStackTrace();
                }finally {
                        if (out!=null)
                                out.delete();
                }
                return null;
        }

        public static byte[] draw(List<String> relations){
                File out = null;
                GraphvizUtil gv = new GraphvizUtil();
                try {
                        out = File.createTempFile("temp",".svg");
                        gv.add(gv.start_graph());
                        for (String line : relations)
                                gv.add(line);
                        gv.add(gv.end_graph());
                        gv.writeGraphToFile(gv.getGraph(gv.getDotSource(), IMAGE_TYPE), out);
                        return IOUtils.toByteArray(new FileInputStream(out));
                } catch (IOException e) {
                        e.printStackTrace();
                }finally {
                        if (out != null)
                                out.delete();
                }
                return null;
        }


        public static void draw(List<String> relations,File out){
                GraphvizUtil gv = new GraphvizUtil();
                gv.add(gv.start_graph());
                for (String line:relations)
                        gv.add(line);
                gv.add(gv.end_graph());
                gv.writeGraphToFile(gv.getGraph(gv.getDotSource(),IMAGE_TYPE),out);
        }

        private static String getPath(String fileName){
                String path = DrawImageUtil.class.getClassLoader().getResource("").getPath();
                int index = path.indexOf("classes");
                path = path.substring(0,index) + "provImage/" + fileName + "." +IMAGE_TYPE;
                path = path.substring(1);
                return path;
        }

        public static void main(String[] args) throws Exception {
                //draw(Arrays.asList(nodeA,nodeB,nodeC),"C:\\Users\\Lulala\\IdeaProjects\\BigDataBeta\\src\\main\\webapp\\WEB-INF\\provImage\\test1.svg");
                File file = new File("F://svgTest.svg");
                FileOutputStream out = new FileOutputStream(file);
                out.write(draw("data1.bmp"));
        }
}
