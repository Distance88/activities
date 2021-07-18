package com.zhang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhang.mapper.AnswerMapper;
import com.zhang.mapper.ProblemMapper;
import com.zhang.pojo.Answer;
import com.zhang.pojo.Problem;
import com.zhang.service.ProblemService;
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
public class ProblemServiceImpl implements ProblemService {

    @Resource
    private ProblemMapper problemMapper;

    @Resource
    private AnswerMapper answerMapper;

    @Override
    public Page<Problem> findProblemList(Integer current) {

        Page<Problem> page = new Page<>(current,5);

        List<Problem> problemList = problemMapper.selectPage(page, null).getRecords();

        for(Problem problem:problemList){
            QueryWrapper<Answer> wrapper = new QueryWrapper<>();

            wrapper.eq("pid",problem.getId());

            List<Answer> answerList = answerMapper.selectList(wrapper);

            problem.setAnswerList(answerList);
        }

        return page.setRecords(problemList);

    }
}