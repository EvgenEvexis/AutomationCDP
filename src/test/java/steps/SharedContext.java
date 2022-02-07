package steps;

import api.dao.AnniversaryWorkflowDto;
import api.dao.EmailTemplateDto;
import api.dao.HomeLibraryDto;
import api.dao.PatronDto;
import api.dao.RegistrationWorkflowDto;
import api.dao.ZipCodeDto;
import dao.ContributorsDto;
import dao.EmailDto;
import dao.FullResourceCard;
import dao.LibraryInfoWidgetConfiguration;
import dao.LinksDto;
import dao.Location;
import dao.RelatedResource;
import dao.sierra.User;
import io.restassured.response.Response;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import pages.modals.CitationExportModal;
import pages.modals.CreateShowcaseModal;
import pages.modals.EmailModal;
import pages.modals.HoldsModal;
import pages.modals.SaveSearchModal;
import pages.modals.SavedSearchEmailModal;
import pages.modals.ShowcaseEmailModal;
import pages.patron.BookmarksFunctionality;
import pages.patron.Bookshelf;
import pages.patron.DumbLoginPage;
import pages.patron.FullResourceCardPage;
import pages.patron.LoginPage;
import pages.patron.MainPage;
import pages.patron.PayflowPage;
import pages.patron.RegistrationPage;
import pages.patron.SearchResultPage;
import pages.patron.UserProfilePage;
import pages.patron.tabs.ShowcasesTab;
import pages.staff.DockingFrameworkPage;
import pages.staff.InnReachSystemPage;
import pages.staff.IntegrationsPage;
import pages.staff.MarcExtractionPage;
import pages.staff.MaterialTypeSettingsPage;
import pages.staff.PatronEngagementPage;
import pages.staff.ShowcasesSuppressedItemsPage;
import pages.staff.StaffLocalizationSettingsPage;
import pages.staff.StaffPage;
import pages.staff.StaffSettingPage;
import pages.staff.ThirdPartyConnectionsPage;
import pages.staff.consortia.ConsortiaCollectionSitesPage;
import pages.staff.consortia.ConsortiaSiteGlobalHeaderPage;
import pages.staff.consortia.ConsortiaSiteLocationsPage;
import pages.staff.consortia.ConsortiaSiteResourcesPage;

public class SharedContext {

  //Shared Page Objects
  public ConsortiaCollectionSitesPage consortiaCollectionSitesPage;
  public ConsortiaSiteGlobalHeaderPage consortiaSiteGlobalHeaderPage;
  public ConsortiaSiteLocationsPage consortiaSiteLocationsPage;
  public ConsortiaSiteResourcesPage consortiaSiteResourcesPage;
  public DockingFrameworkPage dockingFrameworkPage;
  public FullResourceCardPage fullResourceCardPage;
  public InnReachSystemPage innReachSystemPage;
  public DumbLoginPage loginPage;
  public MainPage mainPage;
  public MarcExtractionPage marcExtractionPage;
  public MaterialTypeSettingsPage materialTypeSettingsPage;
  public PatronEngagementPage patronEngagementPage;
  public RegistrationPage registrationPage;
  public SearchResultPage searchResultPage;
  public ShowcasesSuppressedItemsPage showcasesSuppressedItemsPage;
  public IntegrationsPage integrationsPage;
  public StaffPage staffPage;
  public StaffLocalizationSettingsPage staffLocalizationSettingsPage;
  public StaffSettingPage staffSettingPage;
  public ThirdPartyConnectionsPage thirdPartyConnectionsPage;
  public UserProfilePage userProfilePage;
  public PayflowPage payflowPage;

  //Shared Modals
  public CitationExportModal citationExportModal;
  public EmailModal emailModal;
  public SaveSearchModal saveSearchModal;
  public CreateShowcaseModal createShowcaseModal;
  public SavedSearchEmailModal savedSearchEmailModal;
  public ShowcaseEmailModal showcaseEmailModal;
  public HoldsModal holdsModal;

  //Shared Modules
  public BookmarksFunctionality bookmarksModule;
  public Bookshelf bookshelfModule;
  public ShowcasesTab showcasesTab;

  //Shared Variables
  public String expectedAuthor;
  public String expectedDateFormat;
  public String expectedEditionInformation;
  public String expectedFrcUrl;
  public String expectedItemTitle;
  public User user;
  public String savedSearchName;
  public String searchQuery;
  public String marcExtractionName;
  public String innReachLinkUrl;
  public String innReachLinkText;
  public List<String> newPatrons;

  //Tokens
  public String patronAuthToken;
  public String staffAuthToken;

  //Shared Objects
  public ContributorsDto contributors;
  public FullResourceCard frcCard;
  public FullResourceCard fullResourceCardPageWithContributors;
  public List<LinksDto> headerLinks;
  public List<String> listOfStrings;
  public List<RelatedResource> relatedResources;
  public LibraryInfoWidgetConfiguration libraryInfo;
  public Response response;
  public String frcTitle;
  public String location;
  public String showcase;
  public String sierraAuthToken;
  public String staffConfiguration;
  public List<Location> locations;
  public List<EmailDto> testAccounts;
  public List<HomeLibraryDto> homeLibraries;
  //patron
  public ZipCodeDto zipCode;
  public PatronDto patronDto;
  public HomeLibraryDto homeLibraryDto;
  public EmailTemplateDto emailTemplateDto;
  public AnniversaryWorkflowDto anniversaryWorkflowData;
  public RegistrationWorkflowDto registrationWorkflowData;
  public JSONObject patronInfo;
  public JSONArray patronActivities;

}
