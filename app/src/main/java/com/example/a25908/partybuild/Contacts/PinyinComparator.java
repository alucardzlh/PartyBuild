package com.example.a25908.partybuild.Contacts;


import com.example.a25908.partybuild.Model.SortModel;

import java.util.Comparator;

public class PinyinComparator implements Comparator<SortModel>
{

	public int compare(SortModel o1, SortModel o2)
	{
		if (o1.sortLetters.equals("@") || o2.sortLetters.equals("#")) 
			return -1;
		else if (o1.sortLetters.equals("#") || o2.sortLetters.equals("@")) 
			return 1;
	    else 
			return o1.sortLetters.compareTo(o2.sortLetters);
	}

}
