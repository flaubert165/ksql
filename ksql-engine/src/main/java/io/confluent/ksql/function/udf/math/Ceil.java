/*
 * Copyright 2019 Confluent Inc.
 *
 * Licensed under the Confluent Community License (the "License"); you may not use
 * this file except in compliance with the License.  You may obtain a copy of the
 * License at
 *
 * http://www.confluent.io/confluent-community-license
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OF ANY KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations under the License.
 */

package io.confluent.ksql.function.udf.math;

import io.confluent.ksql.function.udf.Udf;
import io.confluent.ksql.function.udf.UdfDescription;
import io.confluent.ksql.function.udf.UdfParameter;
import io.confluent.ksql.function.udf.UdfSchemaProvider;
import io.confluent.ksql.schema.ksql.SqlBaseType;
import io.confluent.ksql.schema.ksql.types.SqlType;
import io.confluent.ksql.util.KsqlException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@UdfDescription(name = "Ceil", description = Ceil.DESCRIPTION)
public class Ceil {

  static final String DESCRIPTION = "Returns the smallest integer greater than or equal to the "
      + "specified numeric expression.";

  @Udf
  public Integer ceil(@UdfParameter final Integer val) {
    return val;
  }

  @Udf
  public Long ceil(@UdfParameter final Long val) {
    return val;
  }

  @Udf
  public Double ceil(@UdfParameter final Double val) {
    return (val == null) ? null : Math.ceil(val);
  }

  @Udf(schemaProvider = "ceilDecimalProvider")
  public BigDecimal ceil(@UdfParameter final BigDecimal val) {
    return val == null
        ? null
        : val.setScale(0, RoundingMode.CEILING).setScale(val.scale(), RoundingMode.UNNECESSARY);
  }

  @UdfSchemaProvider
  public SqlType ceilDecimalProvider(final List<SqlType> params) {
    final SqlType s = params.get(0);
    if (s.baseType() != SqlBaseType.DECIMAL) {
      throw new KsqlException("The schema provider method for Ceil expects a BigDecimal parameter"
          + "type");
    }
    return s;
  }

}
