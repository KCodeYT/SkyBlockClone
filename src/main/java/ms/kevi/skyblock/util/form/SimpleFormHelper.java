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
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementButtonImageData;
import cn.nukkit.form.response.FormResponseSimple;
import cn.nukkit.form.window.FormWindowSimple;
import lombok.RequiredArgsConstructor;
import ms.kevi.skyblock.scheduler.TaskExecutor;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class SimpleFormHelper implements FormHelper<FormResponseSimple> {

    private final Map<ElementButton, Runnable> buttonHandlers = new HashMap<>();
    private final FormWindowSimple form;
    private Consumer<FormResponseSimple> responseHandler;
    private Runnable closeHandler;
    private boolean hasImages = false;

    public SimpleFormHelper button(String text) {
        this.form.addButton(new ElementButton(text));
        return this;
    }

    public SimpleFormHelper button(String text, ImageType type, String image) {
        this.form.addButton(new ElementButton(text, new ElementButtonImageData(type.data, image)));
        if(type == ImageType.URL)
            this.hasImages = true;
        return this;
    }

    public SimpleFormHelper button(String text, Runnable handler) {
        final ElementButton button = new ElementButton(text);
        this.form.addButton(button);
        this.buttonHandlers.put(button, handler);
        return this;
    }

    public SimpleFormHelper button(String text, ImageType type, String image, Runnable handler) {
        final ElementButton button = new ElementButton(text, new ElementButtonImageData(type.data, image));
        this.form.addButton(button);
        this.buttonHandlers.put(button, handler);
        if(type == ImageType.URL)
            this.hasImages = true;
        return this;
    }

    @Override
    public SimpleFormHelper onResponse(Consumer<FormResponseSimple> handler) {
        this.responseHandler = handler;
        return this;
    }

    @Override
    public SimpleFormHelper onClose(Runnable handler) {
        this.closeHandler = handler;
        return this;
    }

    public void send(Player player) {
        if(this.hasImages)
            TaskExecutor.repeating(() -> {
                if(this.closed())
                    TaskExecutor.cancel();
                player.sendExperience();
            }, 5);
        player.showFormWindow(this.form);
        TaskExecutor.repeating(() -> {
            if(this.closed()) {
                if(this.form.getResponse() != null) {
                    final Runnable handler;
                    if((handler = this.buttonHandlers.get(this.form.getResponse().getClickedButton())) != null)
                        handler.run();
                    if(this.responseHandler != null)
                        this.responseHandler.accept(this.form.getResponse());
                } else if(this.closeHandler != null)
                    this.closeHandler.run();
                TaskExecutor.cancel();
            }
        }, 1);
    }

    private boolean closed() {
        return this.form.wasClosed() || this.form.getResponse() != null;
    }

    public SimpleFormHelper ifDo(boolean condition, Consumer<SimpleFormHelper> consumer) {
        if(condition)
            consumer.accept(this);
        return this;
    }

    @RequiredArgsConstructor
    public enum ImageType {
        PATH("path"),
        URL("url");

        final String data;
    }

}
