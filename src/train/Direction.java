package train;

/**
 * Enum representing the possible directions a train can take: from left to right (LR) or from right to left (RL).
 * 
 * This enum defines two instances: LR and RL, each with an overridden toString method providing a human-readable representation.
 * 
 * Authors:
 * - Fabien Dagnat <fabien.dagnat@imt-atlantique.fr>
 * - Philippe Tanguy <philippe.tanguy@imt-atlantique.fr>
 */
public enum Direction {

    /**
     * Represents the direction from left to right.
     */
    LR {
        /**
         * Overrides the toString method to provide a custom string representation.
         * 
         * @return A String describing the direction from left to right.
         */
        @Override
        public String toString() {
            return "from left to right";
        }
    },

    /**
     * Represents the direction from right to left.
     */
    RL {
        /**
         * Overrides the toString method to provide a custom string representation.
         * 
         * @return A String describing the direction from right to left.
         */
        @Override
        public String toString() {
            return "from right to left";
        }
    };
}

