package life.sujunbin.community.community.Contrller;

import life.sujunbin.community.community.pojo.FileDTO;
import life.sujunbin.community.community.utils.Upload_util;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author: 苏俊滨
 * @date: 2019/9/17 22:04
 */
@Controller
public class FileController {
    @RequestMapping("/file/upload")
    @ResponseBody
    public FileDTO upload(HttpServletRequest request, HttpServletResponse response,
                          @RequestParam(value = "editormd-image-file", required = false) MultipartFile file) throws Exception
    {
        String path = Upload_util.upload_one(file, "");
        FileDTO fileDTO = new FileDTO();
        fileDTO.setSuccess(1);
        fileDTO.setUrl(path);
        return fileDTO;


    }
}
