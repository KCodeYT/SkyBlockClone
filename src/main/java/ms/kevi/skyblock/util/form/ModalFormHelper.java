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
import cn.nukkit.form.response.FormResponseModal;
import cn.nukkit.form.window.FormWindowModal;
import lombok.RequiredArgsConstructor;
import ms.kevi.skyblock.scheduler.TaskExecutor;

import java.util.function.Consumer;

@RequiredArgsConstructor
public class ModalFormHelper implements FormHelper<FormResponseModal> {

    private final FormWindowModal form;
    private Consumer<FormResponseModal> responseHandler;
    private Runnable closeHandler;

    @Override
    public ModalFormHelper onResponse(Consumer<FormResponseModal> handler) {
        this.responseHandler = handler;
        return this;
    }

    @Override
    public ModalFormHelper onClose(Runnable handler) {
        this.closeHandler = handler;
        return this;
    }

    public void send(Player player) {
        player.showFormWindow(this.form);
        TaskExecutor.repeating(() -> {
            if(this.form.wasClosed() || this.form.getResponse() != null) {
                if(this.form.getResponse() != null)
                    this.responseHandler.accept(this.form.getResponse());
                else
                    this.closeHandler.run();
                TaskExecutor.cancel();
            }
        }, 1);
    }

    public ModalFormHelper ifDo(boolean condition, Consumer<ModalFormHelper> consumer) {
        if(condition)
            consumer.accept(this);
        return this;
    }

}
