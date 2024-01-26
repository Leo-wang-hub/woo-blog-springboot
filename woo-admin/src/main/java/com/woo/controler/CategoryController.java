package com.woo.controler;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woo.domain.ResponseResult;
import com.woo.domain.dto.CategoryDto;
import com.woo.domain.entity.Category;
import com.woo.domain.vo.CategoryVo;
import com.woo.domain.vo.ExcelCategoryVo;
import com.woo.enums.AppHttpCodeEnum;
import com.woo.service.CategoryService;
import com.woo.util.BeanCopyUtils;
import com.woo.util.WebUtils;
import io.swagger.models.Response;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@RequestMapping("/content/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/listAllCategory")
    public ResponseResult listAll (){
        return categoryService.listAllCategory();
    }
    @GetMapping("/list")
    public ResponseResult list( Integer pageNum, Integer pageSize, Category category){
        return categoryService.listAllCategory(category, pageNum, pageSize);
    }
    @PostMapping
    public ResponseResult addCategory(@RequestBody CategoryDto categoryDto) {
        Category category = BeanCopyUtils.copyBean(categoryDto, Category.class);
        categoryService.save(category);
        return ResponseResult.okResult();
    }
    @DeleteMapping("/{id}")
    public ResponseResult deleteCategory(@PathVariable("id") Integer id){
        categoryService.removeById(id);
        return ResponseResult.okResult();
    }
    @GetMapping("/{id}")
    public ResponseResult getCategoryById(@PathVariable("id") Integer id){
        Category category = categoryService.getById(id);
        CategoryVo categoryVo = BeanCopyUtils.copyBean(category, CategoryVo.class);

        return ResponseResult.okResult(categoryVo);
    }
    @PutMapping
    public ResponseResult updateCategory(@RequestBody Category category){
        categoryService.updateById(category);
        return ResponseResult.okResult();
    }
    //权限控制
    @PreAuthorize("@ps.hasPermission('content:category:export1')")
    @GetMapping("/export")
    public void export(HttpServletResponse response) {
        try {
            System.out.println("进入文件导出的方法");
            //设置下载文件的请求头， 下载下来的Excel文件叫做“分类.xlsx”
            WebUtils.setDownLoadHeader("分类.xlsx",response);
            //获取需要导出的数据
            List<Category> categoryList = categoryService.list();
            List<ExcelCategoryVo> excelCategoryVos = BeanCopyUtils.copyBeanList(categoryList, ExcelCategoryVo.class);
            //把数据写入到Excel中
            EasyExcel.write(response.getOutputStream(), ExcelCategoryVo.class).autoCloseStream(Boolean.FALSE).sheet("文章分类").doWrite(excelCategoryVos);
        } catch (Exception e) {
            e.printStackTrace();
            //如果出现异常 就返回失败的JSON数据
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
            WebUtils.renderString(response, JSON.toJSONString(result));
        }
    }
}
