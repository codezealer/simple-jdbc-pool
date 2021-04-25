package com.codezealer.pool.datasource;

import com.codezealer.pool.api.ILifeCycle;
import com.codezealer.pool.api.IPooledDatasourceConfig;
import com.codezealer.pool.constant.PooledConstant;

public class AbstractPooledDataSourceConfig extends AbstractDataSourceConfig implements IPooledDatasourceConfig, ILifeCycle {


    int maxSize = PooledConstant.MAX_SIZE;
    int minSize = PooledConstant.MIN_SIZE;


    @Override
    public void init() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void setMaxSize(int size) {
        this.maxSize = size;
    }

    @Override
    public void setMinSize(int size) {
        this.minSize = size;
    }
}
