package ee.fujitsu.smit.hotel.tools.properties.helpers;

import lombok.NonNull;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

import java.util.Objects;
import java.util.Properties;

/**
 * Allows YAML files to be used as properties source
 *
 * <p><a href="https://www.baeldung.com/spring-yaml-propertysource">source</a>
 */
public class YamlPropertySourceFactory implements PropertySourceFactory {

  @Override
  public @NonNull PropertySource<?> createPropertySource(
      String name, @NonNull EncodedResource encodedResource) {
    YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
    factory.setResources(encodedResource.getResource());

    Properties properties = factory.getObject();

    return new PropertiesPropertySource(
        Objects.requireNonNull(encodedResource.getResource().getFilename()),
        Objects.requireNonNull(properties));
  }
}
