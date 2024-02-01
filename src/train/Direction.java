package train;

/**
 * Represents the direction a train can travel: either from left to right (LR) or from right to left (RL).
 * This enumeration defines the possible directions and provides a human-readable string representation
 * for each direction.
 * 
 * The use of an enum ensures type safety and makes the code more readable and maintainable.
 * 
 * @author Fabien Dagnat <fabien.dagnat@imt-atlantique.fr>
 * @author Philippe Tanguy <philippe.tanguy@imt-atlantique.fr>
 */
public enum Direction {
    
    // Represents a train moving from left to right
    LR {
        /**
         * Returns a human-readable string representation for the LR (Left to Right) direction.
         * 
         * @return String representation "from left to right".
         */
        @Override
        public String toString() {
            return "from left to right";
        }
    },
    
    // Represents a train moving from right to left
    RL {
        /**
         * Returns a human-readable string representation for the RL (Right to Left) direction.
         * 
         * @return String representation "from right to left".
         */
        @Override
        public String toString() {
            return "from right to left";
        }
    };
}
