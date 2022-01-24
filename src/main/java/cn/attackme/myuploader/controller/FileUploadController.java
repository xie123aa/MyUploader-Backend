package cn.attackme.myuploader.controller;

import cn.attackme.myuploader.config.UploadConfig;
import cn.attackme.myuploader.model.FileMeta;
import cn.attackme.myuploader.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * 文件上传
 */
@RestController
@RequestMapping("/File")
@CrossOrigin
public class FileUploadController {
    @Autowired
    private FileService fileService;

    @PostMapping("/")
    public void upload(String name,
                       String md5,
                       MultipartFile file) throws IOException {
//        boolean isUpload = fileService.checkMd5(md5);
//        if (isUpload) {
//            return;
//        }
        fileService.upload(name, md5,file);
    }

    @GetMapping("/getFileList")
    public List<FileMeta> getUpload() throws IOException {
        return fileService.getFileList();
    }

    @RequestMapping("/download/{fileId}")
    @ResponseBody
    public String downLoad(@PathVariable String fileId, HttpServletResponse response) throws UnsupportedEncodingException {
        FileMeta pictureFile = fileService.getByid(Long.parseLong(fileId));
        File file = new File(pictureFile.getPath());
        //判断文件是否存在，其实不用判断，因为不存在也设置了对应的异常处理
        if (file.exists()) {  //文件存在
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment;fileName=" + java.net.URLEncoder.encode(pictureFile.getName(), "UTF-8"));  //设置下载文件名，解决文件名中文乱码

            byte[] buffer = new byte[1024];
            FileInputStream fis = null; //输入流
            BufferedInputStream bis = null;
            OutputStream os = null; //输出流
            try {
                os = response.getOutputStream();
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                int length;
                while ((length=bis.read(buffer)) != -1) {
                    os.write(buffer,0,length);
                }
                fis.close();
                bis.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return "文件不存在！";
            } catch (IOException e) {
                e.printStackTrace();
                return "下载失败！";
            }

            return null;  //下载成功
        }

        return "文件不存在";
    }

    @RequestMapping("/downloadThumb/{fileId}")
    @ResponseBody
    public String downLoadThumb(@PathVariable String fileId, HttpServletResponse response) throws IOException {
        FileMeta file1 = fileService.getByid(Long.parseLong(fileId));
        String path = UploadConfig.thumbPath+file1.getName();
        File file = new File(path);
        //判断文件是否存在，其实不用判断，因为不存在也设置了对应的异常处理
        if (file.exists()) {  //文件存在
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment;fileName=" + java.net.URLEncoder.encode(file1.getName(), "UTF-8"));  //设置下载文件名，解决文件名中文乱码
            byte[] buffer = new byte[1024];
            FileInputStream fis = null; //输入流
            BufferedInputStream bis = null;
            OutputStream os = null; //输出流
            try {
                os = response.getOutputStream();
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                int length;
                while ((length=bis.read(buffer)) != -1) {
                    os.write(buffer,0,length);
                }
                fis.close();
                bis.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return "文件不存在！";
            } catch (IOException e) {
                e.printStackTrace();
                return "下载失败！";
            }

            return null;  //下载成功
        }

        return "文件不存在";
    }

    }
