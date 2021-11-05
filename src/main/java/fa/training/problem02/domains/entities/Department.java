package fa.training.problem02.domains.entities;

/**
 * @author Edward Dao
 * @version 1.0
 */
public class Department {
    private int deptNo;
    private String deptName;
    private String description;

    public Department() {
    }

    public Department(String deptName, String description) {
        this.deptName = deptName;
        this.description = description;
    }

    public Department(int deptNo, String deptName, String description) {
        this.deptNo = deptNo;
        this.deptName = deptName;
        this.description = description;
    }

    public int getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(int deptNo) {
        this.deptNo = deptNo;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((deptName == null) ? 0 : deptName.hashCode());
        result = prime * result + deptNo;
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Department other = (Department) obj;
        if (deptName == null) {
            if (other.deptName != null)
                return false;
        } else if (!deptName.equals(other.deptName))
            return false;
        if (deptNo != other.deptNo)
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Department: ID=" + deptNo + ", name=" + deptName + ", description=" + description + "]";
    }

}
