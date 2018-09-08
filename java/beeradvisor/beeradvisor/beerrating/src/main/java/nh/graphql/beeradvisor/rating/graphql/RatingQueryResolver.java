package nh.graphql.beeradvisor.rating.graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import graphql.language.Directive;
import graphql.language.Field;
import graphql.schema.*;
import nh.graphql.beeradvisor.rating.Beer;
import nh.graphql.beeradvisor.rating.BeerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Component
public class RatingQueryResolver implements GraphQLQueryResolver {

  private static final String FETCH_EAGER_DIRECTIVE_NAME="fetch_eager";

  private static Logger logger = LoggerFactory.getLogger(RatingQueryResolver.class);

  @Autowired
  private BeerRepository beerRepository;

  public Beer beer(String beerId) {
    return beerRepository.getBeer(beerId);
  }

  public List<Beer> beers(DataFetchingEnvironment dfe) {
   final DataFetchingFieldSelectionSet selectionSet = dfe.getSelectionSet();
    final Map<String, List<Field>> selectedFields = selectionSet.get();

    // determine all requests entities
    final List<String> allRequestedRelations = new LinkedList<>();

    StringBuilder debug = new StringBuilder();

    for (Map.Entry<String, List<Field>> stringListEntry : selectedFields.entrySet()) {
      final String fieldPath = stringListEntry.getKey();
      debug.append(format("Field '" + fieldPath + "'%n"));
      GraphQLFieldDefinition fieldDefinition = selectionSet.getDefinitions().get(fieldPath);
      debug.append(format("%n  ??? Directives => %s", fieldDefinition.getDirectives()));
      final List<Directive> directives = fieldDefinition.getDefinition().getDirectives();
      debug.append(format("%n Definition Directives => %s", directives));

      if (directives.stream().anyMatch(d -> FETCH_EAGER_DIRECTIVE_NAME.equals(d.getName()))) {
        allRequestedRelations.add(fieldPath);
      }


      // if (fieldDefinition != null && fieldDefinition.getD)
      // logger.info("Field '" + fieldPath + "' -> Directives: " +
      // fieldDefinition.getDefinition().getDirectives());
     GraphQLType type = fieldDefinition.getType();
//      debug
//          .append(format("%n field " + fieldPath + " -> Definition: " + (fieldDefinition != null ? type : " no fieldDefinition")));
//
//      if (type instanceof GraphQLObjectType) {
//        allRequestedRelations.add(fieldPath);
//      }

      debug.append(format("%n field " + fieldPath + " -> unwrapped type: "
          + (type != null ? type.getName() + "(" + type.getClass().getName() + ")" : " no fieldDefinition")));
    }

    logger.info("DATA FETCH ENV\n" + debug);

    return beerRepository.findAllWithEntityGraph(allRequestedRelations);

    // return beerRepository.findAll();
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
