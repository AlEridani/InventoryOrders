package side2;

import java.util.ArrayList;

public interface OptionDAO {
	
	public abstract int insert (OptionDTO dto);
	
	public abstract ArrayList<OptionDTO> select ();
	
	public abstract OptionDTO output(String OptionId);
	
	public abstract int delete (String optionId);

}
