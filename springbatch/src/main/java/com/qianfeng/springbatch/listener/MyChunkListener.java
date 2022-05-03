package com.qianfeng.springbatch.listener;

import org.springframework.batch.core.annotation.AfterChunk;
import org.springframework.batch.core.annotation.BeforeChunk;
import org.springframework.batch.core.scope.context.ChunkContext;

/**
 * @program: springbatch
 * @description
 * @author: George
 * @create: 2022-05-02 16:23
 **/
public class MyChunkListener {
    @BeforeChunk
    public void beforeChunk(ChunkContext chunkContext){
        System.out.println(chunkContext.getStepContext().getStepName()+"before---");
    }

    @AfterChunk
    public void afterChunk(ChunkContext chunkContext){

        System.out.println(chunkContext.getStepContext().getStepName()+"after---");
    }
}
