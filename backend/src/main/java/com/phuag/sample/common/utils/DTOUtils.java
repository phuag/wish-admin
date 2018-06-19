
package com.phuag.sample.common.utils;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.phuag.sample.modules.sys.domain.Staff;
import com.phuag.sample.modules.sys.model.StaffDetail;
import com.phuag.sample.modules.sys.model.StaffForm;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Hantsy Bai<hantsy@gmail.com>
 */
public class DTOUtils {

    private static final ModelMapper INSTANCE = new ModelMapper();

    public static <S, T> T map(S source, Class<T> targetClass) {
        return INSTANCE.map(source, targetClass);
    }

    public static <S, T> void mapTo(S source, T dist) {
        INSTANCE.map(source, dist);
    }

    public static <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        List<T> list = null;
        if (source instanceof Page) {
            Page page = (Page) source;
            list = new Page<T>(page.getPageNum(), page.getPageSize(), page.isCount());
            ((Page) list).setReasonable(page.getReasonable());
            ((Page) list).setTotal(page.getTotal());
        } else {
            list = new ArrayList<T>();
        }
        for (int i = 0; i < source.size(); i++) {
            T target = INSTANCE.map(source.get(i), targetClass);
            list.add(target);
        }

        return list;
    }

    public static <S, T> Page<T> mapPage(Page<S> source, Class<T> targetClass) {
        Page<T> list = new Page<T>(source.getPageNum(), source.getPageSize(), source.isCount());
        list.setReasonable(source.getReasonable());
        list.setTotal(source.getTotal());
        for (int i = 0; i < source.size(); i++) {
            T target = INSTANCE.map(source.get(i), targetClass);
            list.add(target);
        }
        return list;
    }
}
