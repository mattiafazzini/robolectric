/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.robolectric.shadows;

import android.util.FloatMath;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

@SuppressWarnings({ "UnusedDeclaration" })
@Implements(FloatMath.class)
public class ShadowFloatMath {

    @Implementation
    protected static float floor(float value) {
        System.out.println("ShadowFloatMath#floor");
        return (float) Math.floor(value);
    }

    @Implementation
    protected static float ceil(float value) {
        System.out.println("ShadowFloatMath#ceil");
        return (float) Math.ceil(value);
    }

    @Implementation
    protected static float sin(float angle) {
        System.out.println("ShadowFloatMath#sin");
        return (float) Math.sin(angle);
    }

    @Implementation
    protected static float cos(float angle) {
        System.out.println("ShadowFloatMath#cos");
        return (float) Math.cos(angle);
    }

    @Implementation
    protected static float sqrt(float value) {
        System.out.println("ShadowFloatMath#sqrt");
        return (float) Math.sqrt(value);
    }
}

