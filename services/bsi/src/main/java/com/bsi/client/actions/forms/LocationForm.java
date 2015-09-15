package com.bsi.client.actions.forms;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.validator.ValidatorForm;

import com.bsi.client.managers.RegionManager;
import com.nms.util.db.BSIException;
import com.nms.util.log.CommonLogger;

public class LocationForm extends ValidatorForm {

	private static Log log = LogFactory.getLog(LocationForm.class);

	private String regionId1 = "";
	private String regionName1 = "";

	private String regionId2 = "";
	private String regionName2 = "";

	private String regionId3 = "";
	private String regionName3 = "";

	private String regionId4 = "";
	private String regionName4 = "";

	private String regionId5 = "";
	private String regionName5 = "";

	private String regionId6 = "";
	private String regionName6 = "";

	public void setRegionId1(String regionId1) {
		this.regionId1 = regionId1;
	}

	public String getRegionId1() {
		return regionId1;
	}

	public void setRegionName1(String regionName1) {
		this.regionName1 = regionName1;
	}

	public String getRegionName1() {
		return regionName1;
	}

	public void setRegionId2(String regionId2) {
		this.regionId2 = regionId2;
	}

	public String getRegionId2() {
		return regionId2;
	}

	public void setRegionName2(String regionName2) {
		this.regionName2 = regionName2;
	}

	public String getRegionName2() {
		return regionName2;
	}

	public void setRegionId3(String regionId3) {
		this.regionId3 = regionId3;
	}

	public String getRegionId3() {
		return regionId3;
	}

	public void setRegionName3(String regionName3) {
		this.regionName3 = regionName3;
	}

	public String getRegionName3() {
		return regionName3;
	}

	public void setRegionId4(String regionId4) {
		this.regionId4 = regionId4;
	}

	public String getRegionId4() {
		return regionId4;
	}

	public void setRegionName4(String regionName4) {
		this.regionName4 = regionName4;
	}

	public String getRegionName4() {
		return regionName4;
	}

	public void setRegionId5(String regionId5) {
		this.regionId5 = regionId5;
	}

	public String getRegionId5() {
		return regionId5;
	}

	public void setRegionName5(String regionName5) {
		this.regionName5 = regionName5;
	}

	public String getRegionName5() {
		return regionName5;
	}

	public void setRegionId6(String regionId6) {
		this.regionId6 = regionId6;
	}

	public String getRegionId6() {
		return regionId6;
	}

	public void setRegionName6(String regionName6) {
		this.regionName6 = regionName6;
	}

	public String getRegionName6() {
		return regionName6;
	}

	// Utility method.

	public void setRegionIdAndName(int totalRegions, String[] ids,
			String[] names) {

		switch (totalRegions) {
		case 1:
			setRegionId1(ids[0]);
			setRegionName1(names[0]);
			break;
		case 2:
			setRegionId2(ids[0]);
			setRegionName2(names[0]);
			setRegionId1(ids[1]);
			setRegionName1(names[1]);
			break;
		case 3:
			setRegionId3(ids[0]);
			setRegionName3(names[0]);
			setRegionId2(ids[1]);
			setRegionName2(names[1]);
			setRegionId1(ids[2]);
			setRegionName1(names[2]);
			break;
		case 4:
			setRegionId4(ids[0]);
			setRegionName4(names[0]);
			setRegionId3(ids[1]);
			setRegionName3(names[1]);
			setRegionId2(ids[2]);
			setRegionName2(names[2]);
			setRegionId1(ids[3]);
			setRegionName1(names[3]);
			break;
		case 5:
			setRegionId5(ids[0]);
			setRegionName5(names[0]);
			setRegionId4(ids[1]);
			setRegionName4(names[1]);
			setRegionId3(ids[2]);
			setRegionName3(names[2]);
			setRegionId2(ids[3]);
			setRegionName2(names[3]);
			setRegionId1(ids[4]);
			setRegionName1(names[4]);
			break;
		case 6:
			setRegionId6(ids[0]);
			setRegionName6(names[0]);
			setRegionId5(ids[1]);
			setRegionName5(names[1]);
			setRegionId4(ids[2]);
			setRegionName4(names[2]);
			setRegionId3(ids[3]);
			setRegionName3(names[3]);
			setRegionId2(ids[4]);
			setRegionName2(names[4]);
			setRegionId1(ids[5]);
			setRegionName1(names[5]);
			break;
		default:
			CommonLogger.logDebug(log, "Region supported are between 1 to 6 ");
			break;
		}
	}

	public Long getSubscribedRegionId() {
		String regionId = null;
		if (regionId6 != null && regionId6.trim().length() > 0
				&& !regionId6.trim().equals("-1")) {
			if (regionId6.equalsIgnoreCase("0")) {
				regionId = regionId5;
			} else {
				regionId = regionId6;
			}
		} else if (regionId5 != null && regionId5.trim().length() > 0
				&& !regionId5.trim().equals("-1")) {
			if (regionId5.equalsIgnoreCase("0")) {
				regionId = regionId4;
			} else {
				regionId = regionId5;
			}
		} else if (regionId4 != null && regionId4.trim().length() > 0
				&& !regionId4.trim().equals("-1")) {
			if (regionId4.equalsIgnoreCase("0")) {
				regionId = regionId3;
			} else {
				regionId = regionId4;
			}
		} else if (regionId3 != null && regionId3.trim().length() > 0
				&& !regionId3.trim().equals("-1")) {
			if (regionId3.equalsIgnoreCase("0")) {
				regionId = regionId2;
			} else {
				regionId = regionId3;
			}
		} else if (regionId2 != null && regionId2.trim().length() > 0
				&& !regionId2.trim().equals("-1")) {
			if (regionId2.equalsIgnoreCase("0")) {
				regionId = regionId1;
			} else {
				regionId = regionId2;
			}
		} else if (regionId1 != null && regionId1.trim().length() > 0
				&& !regionId1.trim().equals("-1")) {
			regionId = regionId1;
		}

		return new Long(regionId);
	}

	public List<Long> getSubscribedRegionIds() throws BSIException {
		List<Long> childRegionIds =new ArrayList<Long>();
		RegionManager regMgr = new RegionManager();
		if (regionId6 != null && regionId6.trim().length() > 0
				&& !regionId6.trim().equals("-1")) {
			if (regionId6.equalsIgnoreCase("0")
					&& !regionId5.equalsIgnoreCase("-1")
					&& !regionId5.equalsIgnoreCase("0")) {
				childRegionIds = regMgr.getRecursiveChildRegions(new Long(
						regionId5));
			} else {
				childRegionIds.add(new Long(regionId6));
			}
		} else if (regionId5 != null && regionId5.trim().length() > 0
				&& !regionId5.trim().equals("-1")) {
			if (regionId5.equalsIgnoreCase("0")
					&& !regionId4.equalsIgnoreCase("-1")
					&& !regionId4.equalsIgnoreCase("0")) {
				childRegionIds = regMgr.getRecursiveChildRegions(new Long(
						regionId4));
			} else {
				childRegionIds.add(new Long(regionId5));
			}
		} else if (regionId4 != null && regionId4.trim().length() > 0
				&& !regionId4.trim().equals("-1")) {
			if (regionId4.equalsIgnoreCase("0")
					&& !regionId3.equalsIgnoreCase("-1")
					&& !regionId3.equalsIgnoreCase("0")) {
				childRegionIds = regMgr.getRecursiveChildRegions(new Long(
						regionId3));
			} else {
				childRegionIds.add(new Long(regionId4));
			}
		} else if (regionId3 != null && regionId3.trim().length() > 0
				&& !regionId3.trim().equals("-1")) {
			if (regionId3.equalsIgnoreCase("0")
					&& !regionId2.equalsIgnoreCase("-1")
					&& !regionId2.equalsIgnoreCase("0")) {
				childRegionIds = regMgr.getRecursiveChildRegions(new Long(
						regionId2));
			} else {
				childRegionIds.add(new Long(regionId3));
			}
		} else if (regionId2 != null && regionId2.trim().length() > 0
				&& !regionId2.trim().equals("-1")) {
			if (regionId2.equalsIgnoreCase("0")
					&& !regionId1.equalsIgnoreCase("-1")
					&& !regionId1.equalsIgnoreCase("0")) {
				childRegionIds = regMgr.getRecursiveChildRegions(new Long(
						regionId1));
			} else {
				childRegionIds.add(new Long(regionId2));
			}
		} else if (regionId1 != null && regionId1.trim().length() > 0
				&& !regionId1.trim().equals("-1")) {
			childRegionIds.add(new Long(regionId1));
		}
		return childRegionIds;
	}

	public static void main(String args[]) {
		LocationForm locn = new LocationForm();
		locn.setRegionId1("2");
		locn.setRegionId2("3");
		locn.setRegionId3("4");
		locn.setRegionId4("0");
		locn.setRegionId5("-1");
		locn.setRegionId6("-1");

		try {
			System.out.println("The locn id is " + locn.getSubscribedRegionIds());
		} catch (BSIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
