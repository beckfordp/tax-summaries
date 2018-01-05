/*
 * Copyright 2018 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package transformers

import errors.AtsError
import models.Amount
import play.api.libs.json.Json
import play.api.libs.json.Json.toJsFieldJsValueWrapper
import uk.gov.hmrc.play.test.UnitSpec
import utils._

import scala.io.Source

class NoAtsErrorTransformerTest extends UnitSpec with AtsJsonDataUpdate {

  val taxpayerDetailsJson = Source.fromURL(getClass.getResource("/taxpayerData/test_individual_utr.json")).mkString
  val parsedTaxpayerDetailsJson = Json.parse(taxpayerDetailsJson)

  "With base data for utr" should {

    "have have an no ets error when " in {

      val originalJson = getClass.getResource("/test_case_2.json")

      val update = Json.obj(
        "ctnIncomeTaxBasicRate" -> new Amount(0.0)
      )

      val transformedJson = transformation(sourceJson = originalJson, tliSlpAtsUpdate = update)

      val returnValue = ATSRawDataTransformer(transformedJson, parsedTaxpayerDetailsJson, "", 2014).atsDataDTO

      returnValue.errors shouldBe Some(AtsError("NoAtsError"))
    }
  }
}