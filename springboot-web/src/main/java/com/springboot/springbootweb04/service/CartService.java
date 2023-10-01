package com.springboot.springbootweb04.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.springbootweb04.entity.Cart;
import com.springboot.springbootweb04.entity.Dto.CourseDto;
import com.springboot.springbootweb04.mapper.CartMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service
public class CartService extends ServiceImpl<CartMapper, Cart> {

    @Resource
    private CartMapper cartMapper;

    public List<CourseDto> selectPage(Integer auid, Integer cur, Integer size) {
        Page<CourseDto> page = new Page<>(cur, size);
        IPage<CourseDto> resultPage = cartMapper.selectP(page, auid);
        return resultPage.getRecords();
    }

    public int getTotalCount(Integer auid) {
        return cartMapper.getTotalCount(auid);
    }

    public List<CourseDto> selectByCourseNameInCart(String cname, Integer auid) {
        return cartMapper.selectByCourseNameInCart(cname, auid);
    }

    public int getTotalCountByCourseName(String cname, Integer auid) {
        return cartMapper.getTotalCountByCourseName(cname, auid);
    }
}
