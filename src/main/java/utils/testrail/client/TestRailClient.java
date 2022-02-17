package utils.testrail.client;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import utils.logging.VegaLogger;
import com.codepine.api.testrail.TestRail;
import com.codepine.api.testrail.TestRail.Projects;
import com.codepine.api.testrail.model.Project;
import com.codepine.api.testrail.model.Result;
import com.codepine.api.testrail.model.ResultField;
import com.codepine.api.testrail.model.Suite;
import com.codepine.api.testrail.model.Run;

public class TestRailClient {

  public static Run run;
  private final static String endPoint = "https://evexis.testrail.io/";
  private final static String username = "evgenevexis@gmail.com";
  private final static String password = "Password123";
  private static int projectId;
  private static int suiteId;
  private static TestRail testRail;

  public static int getProjectId() {
    return projectId;
  }
  public static void setProjectId(int projectId) {
    TestRailClient.projectId = projectId;
  }
  public static int getSuiteId() {
    return suiteId;
  }
  public static void setSuiteId(int suiteId) {
    TestRailClient.suiteId = suiteId;
  }
  public static Run getRun() {
    return run;
  }
  public static void setRun(Run run) {
    TestRailClient.run = run;
  }

  public static TestRail createTestRailInstance() {
    if (testRail == null) {
      testRail = TestRail.builder(endPoint, username, password).build();
    }
    return testRail;
  }

  public static void setProjectSuiteId(String projectName, String suiteName){
    try{
      Projects projects = testRail.projects();
      List<Project> projectList = projects.list().execute();
      int pid = 0;
      int sid = 0;
      for(Project project : projectList){
        if(project.getName().equals(projectName)){
          pid = project.getId();
          setProjectId(pid);
          VegaLogger.info("Project ID: "+pid);
        }
      }
      if(pid !=0){
        List<Suite> suiteList = testRail.suites().list(pid).execute();
        for(Suite s : suiteList){
          String sName = s.getName();
          if(sName.equals(suiteName))
          {
            sid = s.getId();
            setSuiteId(sid);
            VegaLogger.info("Suite ID: "+sid);
          }
        }
      }
    }
    catch(Exception e){
      e.printStackTrace();
    }
  }

  public static void createRun() throws ParseException {
    SimpleDateFormat format = new SimpleDateFormat("dd MMM yyy kk mm s");
    Date date = new Date();
    String dateString = format.format(date);
    String runName = "Automation " + dateString;
    try{
      run = new Run();
      run = testRail.runs().add(getProjectId(),
          run.setSuiteId(getSuiteId())
              .setName(runName)
              .setIncludeAll(false)).execute();
      setRun(run);
    }
    catch(Exception e){
      e.printStackTrace();
    }
  }

  public static void updateRun(int caseId){
    try{
        List<Integer> caseIds = null;
        caseIds.add(caseId);
        getRun().setCaseIds(caseIds);
        testRail.runs().update(getRun()).execute();
    }
    catch(Exception e){
      e.printStackTrace();
    }
  }

  public static void addResult(String comment, int caseId,int statusId){
    try{
      if(null != testRail ){
        List<ResultField> customResultFields = testRail.resultFields().list().execute();
        testRail.results().addForCase(getRun().getId(), caseId,
            new Result().setComment(comment).setStatusId(statusId),customResultFields)
            .execute();
      }
    }
    catch(Exception e){
      e.printStackTrace();
    }
  }

  public static void closeRun(){
    try{
      testRail.runs().close(getRun().getId()).execute();
    }
    catch(Exception e){
      e.printStackTrace();
    }
  }

}
