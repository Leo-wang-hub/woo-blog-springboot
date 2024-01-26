package com.woo.controler;

import com.woo.domain.ResponseResult;
import com.woo.domain.dto.AddTagDto;
import com.woo.domain.dto.EditTagDto;
import com.woo.domain.dto.TagListDto;
import com.woo.domain.entity.Tag;
import com.woo.domain.vo.TagVo;
import com.woo.service.TagService;
import com.woo.util.BeanCopyUtils;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/content/tag")
public class TagController {
    @Autowired
    private TagService tagService;
    @GetMapping("/list")
    public ResponseResult list(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        return tagService.pageTagList(pageNum, pageSize,tagListDto);
    }
    @PostMapping
    public ResponseResult add(@RequestBody AddTagDto tagDto) {
        Tag tag = BeanCopyUtils.copyBean(tagDto, Tag.class);
        System.out.println(tag);
        tagService.save(tag);
        return ResponseResult.okResult();

    }
    @DeleteMapping("/{id}")
    public ResponseResult delete(@PathVariable Long id) {
        tagService.removeById(id);
        return ResponseResult.okResult();
    }
    @GetMapping("/{id}")
    public ResponseResult getInfo(@PathVariable("id") Long id) {
        Tag tag = tagService.getById(id);
        return ResponseResult.okResult(tag);
    }
    @PutMapping
    public ResponseResult edit(@RequestBody EditTagDto tagDto){
        Tag tag = BeanCopyUtils.copyBean(tagDto, Tag.class);
        tagService.updateById(tag);
        return ResponseResult.okResult();

    }
    @GetMapping("listAllTag")
    public ResponseResult listAllTag() {
        List<TagVo> list = tagService.listAllTag();
        return ResponseResult.okResult(list);
    }
}
