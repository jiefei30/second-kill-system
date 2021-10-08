package com.marchsoft.secondkill.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author Wangmingcan
 * @since 2020-08-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@ApiModel(value="Stock对象", description="")
public class Stock extends Model<Stock> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "库存")
    private Integer count;

    @ApiModelProperty(value = "已售")
    private Integer sale;

    @ApiModelProperty(value = "乐观锁，版本号")
    private Integer version;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
