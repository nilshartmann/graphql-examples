package nh.graphql.beerrating.graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import graphql.language.Field;
import graphql.language.FieldDefinition;
import graphql.schema.*;
import nh.graphql.beerrating.BeerRepository;
import nh.graphql.beerrating.model.Beer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityGraph;
import java.util.*;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Component
public class RootResolver implements GraphQLQueryResolver {

  private static Logger logger = LoggerFactory.getLogger(RootResolver.class);

  @Autowired
  private BeerRepository beerRepository;

  public List<Beer> beers(DataFetchingEnvironment dfe) {
    final DataFetchingFieldSelectionSet selectionSet = dfe.getSelectionSet();
    final Map<String, List<Field>> selectedFields = selectionSet.get();

    // determine all requests entities
    final List<String> allRequestedRelations = new LinkedList<>();

    for (Map.Entry<String, List<Field>> stringListEntry : selectedFields.entrySet()) {
      final String fieldPath = stringListEntry.getKey();
      logger.info("Field '" + fieldPath + "'");
      final Field field = stringListEntry.getValue().get(0);
      GraphQLFieldDefinition fieldDefinition = selectionSet.getDefinitions().get(fieldPath);
      GraphQLType type = fieldDefinition.getType();
      logger.info(" field " + fieldPath + " -> Definition: " + (fieldDefinition != null ? type : " no fieldDefinition"));

      type = getUnwrappedType(type);
      if (type instanceof GraphQLObjectType) {
        allRequestedRelations.add(fieldPath);
      }

      logger.info(" field " + fieldPath + " -> unwrapped type: " + (type != null ? type.getName() + "(" + type.getClass().getName() + ")" : " no fieldDefinition"));
    }

    //return beerRepository.findAllWithEntityGraph(allRequestedRelations);

    return beerRepository.findAll();
  }

  private GraphQLType getUnwrappedType(GraphQLType type) {
    if (type instanceof GraphQLNonNull) {
      return getUnwrappedType(((GraphQLNonNull) type).getWrappedType());
    }

    if (type instanceof GraphQLList) {
      return getUnwrappedType(((GraphQLList) type).getWrappedType());
    }

    return type;
  }
}
