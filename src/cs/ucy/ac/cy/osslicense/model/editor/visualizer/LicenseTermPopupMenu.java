package cs.ucy.ac.cy.osslicense.model.editor.visualizer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import cs.ucy.ac.cy.osslicense.model.editor.model.LicenseModel;
import edu.uci.ics.jung.algorithms.layout.GraphElementAccessor;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.AbstractPopupGraphMousePlugin;

public class LicenseTermPopupMenu<V, E> extends AbstractPopupGraphMousePlugin implements MouseListener {
	private LicenseModel licenseModel;
	private ArrayList<String> allRights = new ArrayList<String>();
	private ArrayList<String> allObligations = new ArrayList<String>();
	private ArrayList<String> allAdditionalConditions = new ArrayList<String>();

	public LicenseModel getLicenseModel() {
		return licenseModel;
	}

	public void setLicenseModel(LicenseModel licenseModel) {
		this.licenseModel = licenseModel;
	}

	private void initializeRights() {
		this.allRights.add("MayAddDifferentLicenseTermsToYourModifications");
		this.allRights.add("MayAddYourOwnChoiceOfLicenseIfCodeWasntUnderThisLicense");
		this.allRights.add("MayAddYourOwnCopyrightStatementToYourModifications");
		this.allRights.add("MayCombinedWithFilesUnderOpenOrPropiertaryLicenses");
		this.allRights.add("MayDistributeDerivativeWorksInObjectForm");
		this.allRights.add("MayDistributeDerivativeWorksInSourceForm");
		this.allRights.add("MayDistributeOriginalWorkInObjectForm");
		this.allRights.add("MayDistributeOriginalWorkInSourceForm");
		this.allRights.add("MayGrantRightsToUseAndMakeAvailableOriginalAndEntireWorkModifications");
		this.allRights.add("MayGrantPatentsRightsFromTheContributorToReceipt");
		this.allRights.add("MayMakeModifications");
		this.allRights.add("MayMakeDerivativeWorks");
		this.allRights.add("MaySublicense");
		this.allRights.add("MayPubliclyDisplay");
		this.allRights.add("MayPubliclyPerfom");
		this.allRights.add("MayUseInHouse");
		this.allRights.add("MayCopy");
		this.allRights.add("MayAcceptWarrantyOrAdditionalLiability");
		this.allRights.add("MayCompinedLibraries");
	}

	private void initializeObligations() {
		this.allObligations.add("MustPublishSourceCodeWhenDistributedViaNetwork");
		this.allObligations.add("MustDistibuteCodeUnderThisLicense");
		this.allObligations.add("MustPublishAsLibraryTheModifiedVersion");
		this.allObligations.add("MustLicenseDerivativesWorksUnderCombatibleLicense");
		this.allObligations.add("MustMarkModifications");
		this.allObligations.add("MustNotMisrepresentTheOriginalAuthorOfSoftware");
		this.allObligations.add("MustNotMisrepresentNewVersionAsOriginalSoftware");
		this.allObligations.add("MustOfferSourceCode");
		this.allObligations.add("MustRetainCopyrightNotice");
		this.allObligations.add("MustRetainLicenseNotice");
		this.allObligations.add("MustRetainInSourceCodeOfDerivativeWorkAllTradeMarksPatentCopyrightsOfOriginalWork");
		this.allObligations.add("MustModifeidVersionBeUnderThisLicenseOrGPL");
		this.allObligations.add("NotPermissionToUseTradeMarks");
		this.allObligations.add("NotSublicense");
		this.allObligations.add("MustDistributeCopyOfNoticeText");
		this.allObligations.add("MustDistributeCopiesOfOriginalWorkOrInstructionHowToGetThem");
		this.allObligations.add("MustRenameModifiedVersion");
		this.allObligations.add("MustRetainNoticeInSourceDistribution");
		this.allObligations.add("MustProvideInformationHowToGetSourceCode");
	}

	private void initializeAdditionalConditions() {
		this.allAdditionalConditions.add("LimitedLiability");
		this.allAdditionalConditions.add("ProvideWithoutWarranty");
	}

	public LicenseTermPopupMenu(LicenseModel lModel) {
		super();
		this.licenseModel = lModel;
		initializeAdditionalConditions();
		initializeObligations();
		initializeRights();
	}

	@Override
	protected void handlePopup(MouseEvent e) {
		@SuppressWarnings("unchecked")
		final VisualizationViewer<String, Edge> vv = (VisualizationViewer<String, Edge>) e.getSource();
		final Point2D p = e.getPoint();
		final Point2D ivp = p;

		GraphElementAccessor<String, Edge> pickSupporter = vv.getPickSupport();
		JPopupMenu popupMenu = new JPopupMenu();
		Graph<String, Edge> graph = vv.getGraphLayout().getGraph();

		if (pickSupporter != null) {
			String vertex = pickSupporter.getVertex(vv.getGraphLayout(), ivp.getX(), ivp.getY());

			if (vertex != null) {
				popupMenu.add(new AbstractAction("About " + vertex.toString()) {
					private static final long serialVersionUID = 1L;

					public void actionPerformed(ActionEvent e) {
						JOptionPane.showMessageDialog(vv, vertex.toString());
					}
				});

				popupMenu.add(new AbstractAction("Remove license term") {
					private static final long serialVersionUID = 1L;

					public void actionPerformed(ActionEvent e) {
						if (vertex.equals(licenseModel.getLicenseIdentifier())) {
							JOptionPane.showMessageDialog(vv, "Cannot remove " + vertex.toString());
						} else {
							graph.removeVertex(vertex);
							if (licenseModel.getRights().contains(vertex)) {
								licenseModel.removeRight(vertex);
							} else if (licenseModel.getObligations().contains(vertex)) {
								licenseModel.removeObligation(vertex);
							} else {
								licenseModel.removeAdditionalCondition(vertex);
							}

							vv.repaint();
						}
					}
				});

				if (popupMenu.getComponentCount() > 0) {
					popupMenu.show(vv, e.getX(), e.getY());
				}
			} else {

				LicenseTermMenu rightTerms = new LicenseTermMenu("Insert right",
						(ArrayList<java.lang.String>) this.allRights);
				LicenseTermMenu obligationTerms = new LicenseTermMenu("Insert obligation",
						(ArrayList<java.lang.String>) this.allObligations);
				LicenseTermMenu additionalConditionTerm = new LicenseTermMenu("Insert additional condition",
						(ArrayList<java.lang.String>) this.allAdditionalConditions);

				for (JMenuItem menuItem : rightTerms.getLicenseTermItems()) {
					menuItem.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							if (licenseModel.getRights().contains(menuItem.getText())) {
								JOptionPane.showMessageDialog(vv, menuItem.getText() + " already exists.");
							} else {
								graph.addVertex((String) menuItem.getText());
								graph.addEdge(new Edge("hasRight"), (String) licenseModel.getLicenseIdentifier(),
										(String) menuItem.getText());
								licenseModel.addRight(menuItem.getText());
								vv.repaint();
							}
						}
					});
				}

				for (

				JMenuItem menuItem : obligationTerms.getLicenseTermItems())

				{
					menuItem.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							if (licenseModel.getRights().contains(menuItem.getText())) {
								JOptionPane.showMessageDialog(vv, menuItem.getText() + " already exists.");
							} else {
								graph.addVertex((String) menuItem.getText());
								graph.addEdge(new Edge("hasObligation"), (String) licenseModel.getLicenseIdentifier(),
										(String) menuItem.getText());
								licenseModel.addObligation(menuItem.getText());
								vv.repaint();
							}
						}
					});
				}

				for (

				JMenuItem menuItem : additionalConditionTerm.getLicenseTermItems())

				{
					menuItem.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							if (licenseModel.getRights().contains(menuItem.getText())) {
								JOptionPane.showMessageDialog(vv, menuItem.getText() + " already exists.");
							} else {
								graph.addVertex((String) menuItem.getText());
								graph.addEdge(new Edge("hasAdditionalCondition"),
										(String) licenseModel.getLicenseIdentifier(), (String) menuItem.getText());
								licenseModel.addAdditionalCondition(menuItem.getText());
								vv.repaint();
							}
						}
					});
				}

				popupMenu.add(rightTerms);
				popupMenu.add(obligationTerms);
				popupMenu.add(additionalConditionTerm);

				if (popupMenu.getComponentCount() > 0)

				{
					popupMenu.show(vv, e.getX(), e.getY());
				}

			}

		}
	}

	public ArrayList<String> getAllAdditionalConditions() {
		return allAdditionalConditions;
	}

	public void setAllAdditionalConditions(ArrayList<String> allAdditionalConditions) {
		this.allAdditionalConditions = allAdditionalConditions;
	}

	public ArrayList<String> getAllObligations() {
		return allObligations;
	}

	public void setAllObligations(ArrayList<String> allObligations) {
		this.allObligations = allObligations;
	}

	public ArrayList<String> getAllRights() {
		return allRights;
	}

	public void setAllRights(ArrayList<String> allRights) {
		this.allRights = allRights;
	}

}
