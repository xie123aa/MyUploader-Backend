package cn.attackme.myuploader.mapper;

import cn.attackme.myuploader.model.FileMeta;
import tk.mybatis.mapper.common.Mapper;
import java.util.List;

public interface FileMetaMapper extends Mapper<FileMeta>{
    /**
     * 通过主键获取一行数据
     * @return
     */
    FileMeta getById(Long id);

    /**
     * 插入一行数据
     * @param file
     * @return
     */
    int save(FileMeta file);

    /**
     * 更新一行数据
     * @param file
     * @return
     */
    int update(FileMeta file);

    /**
     * 删除一行数据
     * @param id
     * @return
     */
    int deleteById(Long id);

    /**
     * 根据一个或多个属性获取File
     * @param file
     * @return
     */
    FileMeta getByFile(FileMeta file);

    List<FileMeta> getFileList();
}
