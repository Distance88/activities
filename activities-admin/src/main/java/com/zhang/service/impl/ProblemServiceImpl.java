package com.zhang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhang.mapper.ProblemMapper;
import com.zhang.pojo.Problem;
import com.zhang.service.ProblemService;
import com.zhang.utils.RedisUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Problem)表服务接口
 *
 * @author Distance
 * @since 2020-10-15 20:40:06
 */
@Service
@SuppressWarnings("unchecked")
public class ProblemServiceImpl implements ProblemService {

    @Resource
    private ProblemMapper problemMapper;

    @Resource
    private RedisUtil redisUtil;


    @Override
    public Page<Problem> findProblemList(Integer current) {

        String key = "problem_current_"+current;


        boolean hasKey = redisUtil.hasKey(key);

        if(hasKey){
            return (Page<Problem>) redisUtil.get(key);
        }

        Page<Problem> page = new Page<>(current,5);

        IPage<Problem> problemIPage = problemMapper.selectPage(page, null);

        redisUtil.set(key,problemIPage);

        return (Page<Problem>) problemIPage;
    }



    @Override
    public Page<Problem> findProblemByCondition(Problem problem, Integer current) {

        Page<Problem> page = new Page<>(current,5);

        QueryWrapper<Problem> wrapper = new QueryWrapper<>();

        wrapper.eq("id",problem.getId())
                .or()
                .eq("level",problem.getLevel())
                .or()
                .between("create_time",problem.getStartTime(),problem.getEndTime());




        return (Page<Problem>) problemMapper.selectPage(page,wrapper);
    }

    @Override
    public void deleteProblemById(Integer id) {

        redisUtil.deleteByPrex("problem_current_*");

        problemMapper.deleteById(id);

    }

    @Override
    public void insertProblem(Problem problem) {

        redisUtil.deleteByPrex("problem_current_*");

        problemMapper.insert(problem);

    }

}