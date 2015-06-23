package ca.pandp.main;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ServiceConfigurationError;
import java.util.Set;

import javax.lang.model.element.Element;
import javax.lang.model.element.Name;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;

import ca.pandp.plugins.consumers.Consumable;
import ca.pandp.plugins.producers.Producible;
import ca.pandp.shared.domainobjects.BeanModel;

import com.google.auto.common.BasicAnnotationProcessor;
import com.google.auto.common.MoreElements;
import com.google.auto.common.MoreTypes;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.SetMultimap;

public class ThreeBProcessor extends BasicAnnotationProcessor {

	@Override
	protected Iterable<? extends ProcessingStep> initSteps() {
		return ImmutableList.of(gatherStep);
	}

	Set<Element> producers = null;
	Set<Element> consumers = null;

	protected void postProcess() {
		System.out.println("runnning");

		for (Element p : producers) {
			for (Element c : consumers) {
				try {

					Producer pAnn = p.getAnnotation(Producer.class);
					Consumer cAnn = p.getAnnotation(Consumer.class);

					String pName = MoreElements.asType(p).getQualifiedName()
							.toString();
					String cName = MoreElements.asType(c).getQualifiedName()
							.toString();
					System.out.println(pName);
					System.out.println(cName);

					Producible p2 = (Producible) Class.forName(pName)
							.newInstance();
					Consumable c2 = (Consumable) Class.forName(cName)
							.newInstance();

					c2.consume(p2.produce());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	};

	ProcessingStep gatherStep = new ProcessingStep() {
		@Override
		public void process(
				SetMultimap<Class<? extends Annotation>, Element> elementsByAnnotation) {

			producers = elementsByAnnotation.get(Producer.class);
			consumers = elementsByAnnotation.get(Consumer.class);
		}

		@Override
		public Set<Class<? extends Annotation>> annotations() {
			final HashSet x = new HashSet();
			x.add(Producer.class);
			x.add(Consumer.class);
			return x;
		}
	};
}
