package ca.pandp.main;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;

import com.google.auto.common.BasicAnnotationProcessor;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multiset.Entry;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;

@SupportedAnnotationTypes("ca.pandp.main.Producer")
@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class Processor extends BasicAnnotationProcessor {

	@Override
	protected Iterable<? extends ProcessingStep> initSteps() {
		return ImmutableList.of(new ProcessingStep() {

			@Override
			public void process(
					SetMultimap<Class<? extends Annotation>, Element> elementsByAnnotation) {

				System.out.println("Found these annotations:");
				for (Map.Entry<Class<? extends Annotation>, Collection<Element>> entry : elementsByAnnotation
						.asMap().entrySet()) {
					System.out.println("  " + entry.getKey());
					for (Element e : entry.getValue()) {
						System.out.println(e);
					}
				}
			}

			@Override
			public Set<? extends Class<? extends Annotation>> annotations() {
				//HashSet<Class<?>> x = Sets.newHashSet();
				//x.add(ca.pandp.main.Producer.class);
				//x.add(ca.pandp.main.Consumer.class);
				return null;
			}
		});
	}
}
