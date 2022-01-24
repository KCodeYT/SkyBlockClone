/*
 * Copyright 2022 KCodeYT
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

package ms.kevi.skyblock.util.form;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.form.window.FormWindowModal;
import cn.nukkit.form.window.FormWindowSimple;

import java.util.function.Consumer;

public interface FormHelper<R extends FormResponse> {

    static SimpleFormHelper simple(String title) {
        return simple(title, "");
    }

    static SimpleFormHelper simple(String title, String content) {
        return new SimpleFormHelper(new FormWindowSimple(title, content));
    }

    static ModalFormHelper modal(String title, String content, String trueButtonText, String falseButtonText) {
        return new ModalFormHelper(new FormWindowModal(title, content, trueButtonText, falseButtonText));
    }

    static CustomFormHelper custom(String title) {
        return new CustomFormHelper(new FormWindowCustom(title));
    }

    FormHelper<R> onResponse(Consumer<R> handler);

    FormHelper<R> onClose(Runnable handler);

    void send(Player player);

}
