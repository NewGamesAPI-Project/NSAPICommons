package net.cg360.nsapi.commons.id;

import net.cg360.nsapi.commons.Utility;

import java.util.Objects;
import java.util.regex.Pattern;

public class Identifier {

    protected String namespace;
    protected String id;

    public Identifier (String namespace, String id) {
        this(new Namespace(namespace), id);
    }
    public Identifier (Namespace namespace, String id) {
        // Namespace IDs are checked thus can be trusted.
        this.namespace = namespace.getNamespaceString();

        if(nullCheck(id)) {

            if(id.contains(":")) {
                String i = id.split(Pattern.quote(":"))[1]; // There should be at least index 0 + index 1

                if(nullCheck(i)){
                    this.id = i.trim().toLowerCase();
                }

            } else {
                // Not spaces, no colons, all checks passed.
                this.id = id.trim().toLowerCase();
            }
        }
    }



    /**
     * Little constructor utility method for checking if the identifier is
     * valid, else replacing it with a random string of characters.
     * @param i the identifier in
     * @return has the identifier been left the same?
     */
    protected boolean nullCheck(String i) {

        // Check it isn't null, length 0, or just spaces.
        if(i == null || i.trim().length() == 0) {
            this.id = "gen_"+ Utility.generateUniqueToken(6, 6); // What are the chances of this clashing
            return false;
        }
        return true;
    }



    /** @return the namespace of the identifier.*/
    public String getNamespace() { return namespace; }
    /** @return the id element of the identifier. */
    public String getObjectId() { return id; }

    /** @return the full string identifier.*/
    public String getID() { return namespace+":"+id; }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Identifier that = (Identifier) o;
        return Objects.equals(namespace, that.namespace) && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(namespace, id);
    }
}
