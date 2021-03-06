/*
Copyright 2014 Google Inc. All rights reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package com.google.cloud.genomics.dataflow.functions.pca;

import static org.junit.Assert.assertEquals;

import com.google.cloud.genomics.dataflow.utils.DataUtils;
import com.google.genomics.v1.Variant;
import com.google.genomics.v1.VariantCall;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;
import java.util.Collections;

@RunWith(JUnit4.class)
public class ExtractSimilarCallsetsTest {

  @Test
  public void testGetSamplesWithVariant() throws Exception {
    ExtractSimilarCallsets doFn = new ExtractSimilarCallsets();

    Variant variant = Variant.newBuilder().build();
    assertEquals(Collections.emptyList(), doFn.getSamplesWithVariant(variant));

    VariantCall ref = DataUtils.makeVariantCall("ref", 0, 0);
    variant = Variant.newBuilder().addCalls(ref).build();
    assertEquals(Collections.emptyList(), doFn.getSamplesWithVariant(variant));

    VariantCall alt1 = DataUtils.makeVariantCall("alt1", 1, 0);
    variant = Variant.newBuilder().addCalls(ref).addCalls(alt1).build();
    assertEquals(Collections.singletonList("alt1"), doFn.getSamplesWithVariant(variant));

    VariantCall alt2 = DataUtils.makeVariantCall("alt2", 0, 1);
    VariantCall alt3 = DataUtils.makeVariantCall("alt3", 1, 1);
    variant = Variant.newBuilder().addCalls(ref)
        .addCalls(alt1).addCalls(alt2).addCalls(alt3).build();
    assertEquals(Arrays.asList("alt1", "alt2", "alt3"), doFn.getSamplesWithVariant(variant));
  }
}
