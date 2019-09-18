package life.sujunbin.community.community.utils;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * 处理上传文件的工具类
 * */
public class Upload_util {

    @Value("${File.upload}")
    public static String ROOT_PATH ;


    /*
     * 单个文件处理
     * */
    public static String upload_one(MultipartFile file, String filepath) throws Exception {
        String mvc_path = null;
        if (filepath == null || filepath.equals("")) {

            if (file.isEmpty()) {
                return filepath;
            }
            //要上传的磁盘路径
            String rootPath = ROOT_PATH;
            String path = copyimage(file,rootPath);
            mvc_path = "/uploaded_files" + path;
            System.out.println(mvc_path);
            return mvc_path;
        } else {
            String filepaths = "D:/luntan" + filepath;
            File to_file = new File(filepaths);
            file.transferTo(to_file);
        }
        return filepath;
    }


    public static String copyimage(MultipartFile file, String rootPath)throws Exception
    {
        //文件名
        String fileName = file.getOriginalFilename();
        //截取文件类型
        String fileType = fileName.substring(fileName.indexOf(".") + 1);
        //创建以日期为名字的文件夹
        String dateFolder = "/" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "/";
        //拼接文件的相对路径
        String path = dateFolder + MyUtils.getRandomString(15) + "." + fileType;
        //创建文件夹
        new File(rootPath + dateFolder).mkdir();
        //写入文件
        File to_file = new File(rootPath + path);
        file.transferTo(to_file);
        return path;
    }


}
