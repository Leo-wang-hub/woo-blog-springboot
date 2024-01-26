package com.woo.controler;

import com.woo.domain.ResponseResult;
import com.woo.domain.entity.Link;
import com.woo.domain.vo.PageVo;
import com.woo.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/link")
public class LinkController {
    @Autowired
    private LinkService linkService;
    @GetMapping("list")
    public ResponseResult list(Link link, Integer pageNum, Integer pageSize) {
        PageVo pageVo = linkService.selectLinkPage(link,pageNum,pageSize);
        return ResponseResult.okResult(pageVo);
    }
    @PostMapping
    public ResponseResult add(@RequestBody Link link) {
        linkService.save(link);
        return ResponseResult.okResult();
    }
    @PutMapping
    public ResponseResult edit(@RequestBody Link link) {
        linkService.updateById(link);
        return ResponseResult.okResult();
    }
    @PutMapping("/changeLinkStatus")
    public  ResponseResult changeStatus (@RequestBody Link link) {
        linkService.updateById(link);
        return ResponseResult.okResult();

    }
    @GetMapping("{id}")
    public ResponseResult geInfo(@PathVariable("id") Long id){
        return ResponseResult.okResult(linkService.getById(id));
    }
    @DeleteMapping("{id}")
    public ResponseResult delete(@PathVariable("id") Long id) {
        linkService.removeById(id);
        return ResponseResult.okResult();
    }
}
