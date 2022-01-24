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
import cn.nukkit.form.element.*;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.window.FormWindowCustom;
import lombok.RequiredArgsConstructor;
import ms.kevi.skyblock.scheduler.TaskExecutor;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class CustomFormHelper implements FormHelper<FormResponseCustom> {

    private final FormWindowCustom form;
    private Consumer<FormResponseCustom> responseHandler;
    private Runnable closeHandler;

    public CustomFormHelper dropdown(String text) {
        this.form.addElement(new ElementDropdown(text));
        return this;
    }

    public CustomFormHelper dropdown(String text, String... options) {
        this.form.addElement(new ElementDropdown(text, Arrays.asList(options)));
        return this;
    }

    public CustomFormHelper dropdown(String text, List<String> options, int defaultOption) {
        this.form.addElement(new ElementDropdown(text, options, defaultOption));
        return this;
    }

    public CustomFormHelper input(String text) {
        this.form.addElement(new ElementInput(text));
        return this;
    }

    public CustomFormHelper input(String text, String placeholder) {
        this.form.addElement(new ElementInput(text, placeholder));
        return this;
    }

    public CustomFormHelper input(String text, String placeholder, String defaultText) {
        this.form.addElement(new ElementInput(text, placeholder, defaultText));
        return this;
    }

    public CustomFormHelper label(String text) {
        this.form.addElement(new ElementLabel(text));
        return this;
    }

    public CustomFormHelper slider(String text, float min, float max) {
        this.form.addElement(new ElementSlider(text, min, max));
        return this;
    }

    public CustomFormHelper slider(String text, float min, float max, int step) {
        this.form.addElement(new ElementSlider(text, min, max, step));
        return this;
    }

    public CustomFormHelper slider(String text, float min, float max, int step, float defaultValue) {
        this.form.addElement(new ElementSlider(text, min, max, step, defaultValue));
        return this;
    }

    public CustomFormHelper stepSlider(String text, String... steps) {
        this.form.addElement(new ElementStepSlider(text, Arrays.asList(steps)));
        return this;
    }

    public CustomFormHelper stepSlider(String text, List<String> steps, int defaultStep) {
        this.form.addElement(new ElementStepSlider(text, steps, defaultStep));
        return this;
    }

    public CustomFormHelper toggle(String text) {
        this.form.addElement(new ElementToggle(text));
        return this;
    }

    public CustomFormHelper toggle(String text, boolean defaultValue) {
        this.form.addElement(new ElementToggle(text, defaultValue));
        return this;
    }

    @Override
    public CustomFormHelper onResponse(Consumer<FormResponseCustom> handler) {
        this.responseHandler = handler;
        return this;
    }

    @Override
    public CustomFormHelper onClose(Runnable handler) {
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

    public CustomFormHelper ifDo(boolean condition, Consumer<CustomFormHelper> consumer) {
        if(condition)
            consumer.accept(this);
        return this;
    }

}
