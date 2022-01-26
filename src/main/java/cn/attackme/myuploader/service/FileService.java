package cn.attackme.myuploader.service;

import cn.attackme.myuploader.config.UploadConfig;
import cn.attackme.myuploader.mapper.FileMetaMapper;
import cn.attackme.myuploader.model.FileMeta;
import cn.attackme.myuploader.utils.FileUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import static cn.attackme.myuploader.utils.FileUtils.generateFileName;
import static cn.attackme.myuploader.utils.UploadUtils.*;

/**
 * 文件上传服务
 */
@Service
public class FileService {
    @Autowired
    private FileMetaMapper fileDao;


    /**
     * 上传文件
     * @param md5
     * @param file
     */
    public void upload(String name,
                       String md5,
                       MultipartFile file) throws IOException {
        String path = UploadConfig.path + generateFileName();
        FileUtils.write(path, file.getInputStream());
        //写入缩略图
        String thumbPath =UploadConfig.thumbPath+name;
        Thumbnails.of(path)
                .size(200, 300)
                .toFile(thumbPath);
        //写入原文件
        fileDao.save(new FileMeta(name, md5, path, new Date()));
    }

    /**
     * 分块上传文件
     * @param md5
     * @param size
     * @param chunks
     * @param chunk
     * @param file
     * @throws IOException
     */
    public void uploadWithBlock(String name,
                                String md5,
                                Long size,
                                Integer chunks,
                                Integer chunk,
                                MultipartFile file) throws IOException {
        //1.生成名称，以<文件名称，分块上传记录数组>生成分块记录
        String fileName = getFileName(md5, chunks);
        FileUtils.writeWithBlok(UploadConfig.path + fileName, size, file.getInputStream(), file.getSize(), chunks, chunk);
        //2.记录上传分块的标识
        addChunk(md5,chunk);
        //3上传完毕存储表中
        if (isUploaded(md5)) {
            //4.上传完毕删除记录
            removeKey(md5);
            fileDao.save(new FileMeta(name, md5,UploadConfig.path + fileName, new Date()));
        }
    }

    /**
     * 检查Md5判断文件是否已上传
     * @param md5
     * @return
     */
    public boolean checkMd5(String md5) {
        FileMeta file = new FileMeta();
        file.setMd5(md5);
        return fileDao.getByFile(file) == null;
    }

    public List<FileMeta> getFileList(){
        return fileDao.getFileList();
    }


    public FileMeta getByid(Long id){
        return fileDao.getById(id);
    }

    public PageInfo findPage(int page, int pageSize){
        PageHelper.startPage(page,pageSize);
        List<FileMeta> fileList=fileDao.getFileList();
        PageInfo pageInfo = new PageInfo(fileList);
        return pageInfo;
    }


}