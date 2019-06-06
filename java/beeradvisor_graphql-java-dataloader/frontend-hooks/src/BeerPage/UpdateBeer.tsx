import React from "react";
import { BeerPageQuery_beer } from "./querytypes/BeerPageQuery";
import { useAuthContext } from "AuthContext";
import styles from "./Form.module.scss";
import gql from "graphql-tag";
import { useMutation } from "@apollo/react-hooks";
import { UpdateBeerNameMutation, UpdateBeerNameMutationVariables } from "./querytypes/UpdateBeerNameMutation";

type UpdateBeerProps = {
  beer: BeerPageQuery_beer;
};

const UPDATE_BEERNAME_MUTATION = gql`
  mutation UpdateBeerNameMutation($beerId: ID!, $newName: String!) {
    updatedBeer: updateBeerName(beerId: $beerId, newName: $newName) {
      id
      name
    }
  }
`;

export default function UpdateBeer({ beer }: UpdateBeerProps) {
  const { auth } = useAuthContext();
  const [updateBeerName, { error, data }] = useMutation<UpdateBeerNameMutation, UpdateBeerNameMutationVariables>(
    UPDATE_BEERNAME_MUTATION
  );

  function onBeerNameChange(newBeerName: string) {
    updateBeerName({
      variables: {
        beerId: beer.id,
        newName: newBeerName
      }
    });
  }

  if (!auth || !("auth" in auth) || !(auth.auth.username === "Nils")) {
    return null;
  }

  // we don't care about error handling here
  console.log("error", error);

  // we don't care about the result here => it's just used for demo purposes
  console.log("data", data);
  return <UpdateBeerForm beername={beer.name} onNewBeerName={onBeerNameChange} />;
}

type UpdateBeerFormProps = {
  beername: string;
  onNewBeerName(newBeerName: string): void;
};

function UpdateBeerForm({ beername, onNewBeerName }: UpdateBeerFormProps) {
  const [newName, setNewName] = React.useState(beername);
  return (
    <div className={styles.Form}>
      <h3>Your admin and can change the name of {beername}</h3>
      <form>
        <fieldset>
          <div>
            <label>New name:</label> <input type="text" value={newName} onChange={e => setNewName(e.currentTarget.value)} />
          </div>
        </fieldset>
        <div>
          <button
            onClick={e => {
              e.preventDefault();
              onNewBeerName(newName);
            }}
          >
            Set name for {beername}
          </button>
        </div>
      </form>
    </div>
  );
}
