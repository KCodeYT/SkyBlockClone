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

package ms.kevi.skyblock.scheduler;

import cn.nukkit.Server;
import cn.nukkit.scheduler.ServerScheduler;
import cn.nukkit.scheduler.Task;

public class TaskExecutor {

    private static final ServerScheduler SCHEDULER = Server.getInstance().getScheduler();

    public static void sync(Runnable runnable) {
        SCHEDULER.scheduleTask(null, runnable, false);
    }

    public static void delayed(Runnable runnable, int delay) {
        SCHEDULER.scheduleDelayedTask(null, runnable, delay,false);
    }

    public static void repeating(Runnable runnable, int period) {
        SCHEDULER.scheduleRepeatingTask(null, asTask(runnable), period,false);
    }

    public static void delayedRepeating(Runnable runnable, int delay, int period) {
        SCHEDULER.scheduleDelayedRepeatingTask(null, asTask(runnable), delay, period,false);
    }

    public static void async(Runnable runnable) {
        SCHEDULER.scheduleTask(null, runnable, true);
    }

    public static void delayedAsync(Runnable runnable, int delay) {
        SCHEDULER.scheduleDelayedTask(null, runnable, delay,true);
    }

    public static void repeatingAsync(Runnable runnable, int period) {
        SCHEDULER.scheduleRepeatingTask(null, asTask(runnable), period,true);
    }

    public static void delayedRepeatingAsync(Runnable runnable, int delay, int period) {
        SCHEDULER.scheduleDelayedRepeatingTask(null, asTask(runnable), delay, period, true);
    }

    public static void cancel() {
        throw CancelException.INSTANCE;
    }

    private static Task asTask(Runnable runnable) {
        return new Task() {
            @Override
            public void onRun(int tick) {
                try {
                    runnable.run();
                } catch(CancelException e) {
                    this.getHandler().cancel();
                }
            }
        };
    }

}
