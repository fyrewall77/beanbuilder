package test;

import ca.pandp.main.Consumer;
import ca.pandp.plugins.consumers.Consumable;
import ca.pandp.shared.domainobjects.BeanModel;

import javax.validation.constraints.NotNull;

/**
 * Created by Micha "Micha Did It" Pringle on Jun 07, 2015.
 * <p>
 * .
 */

@net.jcip.annotations.Immutable
@net.jcip.annotations.ThreadSafe
@Consumer
public class ConsumableTest implements Consumable {

    public ConsumableTest() {
    }

    public void consume(@NotNull BeanModel bean) {
        System.out.println("Consuming " + bean);
    }
}
