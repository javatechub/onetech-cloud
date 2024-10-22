package cn.cloud.onetech.mock.generator;

import cn.cloud.onetech.mock.meta.DataMetadata;

/**
 * @author heguanhui
 * @desc
 * @date 2024/10/22 17:06
 */
public interface DataGenerator<DataValue> {

    DataValue invoke(DataMetadata dataMetadata);

}
