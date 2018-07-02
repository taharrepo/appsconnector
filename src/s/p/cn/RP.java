package s.p.cn;

import java.io.Serializable;

public class RP implements Serializable, Comparable<RP> {    
	/**
	 * 
	 */
	private static final long serialVersionUID = 2104463512517616448L;
	

	private String name;
    
    private String value;
    
    
    /**
     * @param name
     * @param value
     */
    public RP(String name, String value) {
        this.name = name;
        this.value = value;
    }

    /**
     * @param name
     * @param value
     */
    public RP(String name, double value) {
        this.name = name;
        this.value = String.valueOf(value);
    }

    /**
     * @param name
     * @param value
     */
    public RP(String name, int value) {
        this.name = name;
        this.value = String.valueOf(value);
    }

    /**
     * @return
     */
    public String getName(){
        return name;
    }
    /**
     * @return
     */
    public String getValue(){
        return value;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + value.hashCode();
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object obj) {
        if (null == obj) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj instanceof RP) {
            RP that = (RP) obj;
            return this.name.equals(that.name) &&
                this.value.equals(that.value);
        }
        return false;
    }
    
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(RP o) {
		int compared = name.compareTo(o.name);
        if (0 == compared) {
            compared = value.compareTo(o.value);
        }
        return compared;
	}
    
}
