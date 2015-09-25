package cs.ucy.ac.cy.osslicense.model.editor.wizard;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.custom.CCombo;

public class LicenseModelWizardPage extends WizardPage {
	private Composite container;
	private List licenseRights;
	private List licenseObligations;
	private Button hasProvidedWithoutWarranty;
	private Button hasLimitedLiability;
	private CCombo licenseTitle;
	private WizardPage currentPage;

	protected LicenseModelWizardPage(String pageName) {
		super(pageName);
		setPageComplete(false);
		setTitle("License Model Wizard: ");
		setDescription("New License Model");
		currentPage = this;
	}

	public String getLicenseTitle() {
		return licenseTitle.getText();
	}

	public String[] getLicenseRights() {
		return licenseRights.getItems();
	}

	public String[] getLicenseObligations() {
		return licenseObligations.getItems();
	}

	public boolean hasLimitedLiability() {
		return hasLimitedLiability.getSelection();
	}

	public boolean hasProvidedWithoutWarranty() {
		return hasProvidedWithoutWarranty.getSelection();
	}

	@Override
	public void createControl(Composite parent) {
		container = new Composite(parent, SWT.NONE);
		setControl(container);
		container.setLayout(null);

		Label lblRights = new Label(container, SWT.NONE);
		lblRights.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		lblRights.setBounds(5, 47, 249, 15);
		lblRights.setText("Rights:");

		List rightList = new List(container, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		rightList.setBounds(5, 70, 249, 146);
		rightList.setTouchEnabled(true);
		rightList.setItems(new String[] { "MayAddDifferentLicenseTermsToYourModifications",
				"MayAddYourOwnChoiceOfLicenseIfCodeWasntUnderThisLicense",
				"MayAddYourOwnCopyrightStatementToYourModifications",
				"MayCombinedWithFilesUnderOpenOrPropiertaryLicenses", "MayDistributeDerivativeWorksInObjectForm",
				"MayDistributeDerivativeWorksInSourceForm", "MayDistributeOriginalWorkInObjectForm",
				"MayDistributeOriginalWorkInSourceForm",
				"MayGrantRightsToUseAndMakeAvailableOriginalAndEntireWorkModifications",
				"MayGrantPatentsRightsFromTheContributorToReceipt", "MayMakeModifications", "MayMakeDerivativeWorks",
				"MaySublicense", "MayPubliclyDisplay", "MayPubliclyPerfom", "MayUseInHouse", "MayCopy",
				"MayAcceptWarrantyOrAdditionalLiability", "MayCompinedLibraries" });

		licenseRights = new List(container, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		licenseRights.setBounds(438, 70, 245, 146);
		licenseRights.setItems(new String[] {});

		Button addRight = new Button(container, SWT.NONE);
		addRight.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				for (int selectionIndex : rightList.getSelectionIndices()) {
					licenseRights.add(rightList.getItem(selectionIndex));
					rightList.remove(selectionIndex);
				}
			}
		});
		addRight.setBounds(290, 117, 110, 25);
		addRight.setText(">>");

		Button removeRight = new Button(container, SWT.NONE);
		removeRight.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				for (int selectionIndex : licenseRights.getSelectionIndices()) {
					rightList.add(rightList.getItem(selectionIndex));
					licenseRights.remove(selectionIndex);
				}
			}
		});
		removeRight.setBounds(290, 148, 110, 25);
		removeRight.setText("<<");

		List obligationList = new List(container, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		obligationList.setItems(new String[] { "MustPublishSourceCodeWhenDistributedViaNetwork",
				"MustDistibuteCodeUnderThisLicense", "MustPublishAsLibraryTheModifiedVersion",
				"MustLicenseDerivativesWorksUnderCombatibleLicense", "MustMarkModifications",
				"MustNotMisrepresentTheOriginalAuthorOfSoftware", "MustNotMisrepresentNewVersionAsOriginalSoftware",
				"MustOfferSourceCode", "MustRetainCopyrightNotice", "MustRetainLicenseNotice",
				"MustRetainInSourceCodeOfDerivativeWorkAllTradeMarksPatentCopyrightsOfOriginalWork",
				"MustModifeidVersionBeUnderThisLicenseOrGPL", "NotPermissionToUseTradeMarks", "NotSublicense",
				"MustDistributeCopyOfNoticeText", "MustDistributeCopiesOfOriginalWorkOrInstructionHowToGetThem",
				"MustRenameModifiedVersion", "MustRetainNoticeInSourceDistribution",
				"MustProvideInformationHowToGetSourceCode" });
		obligationList.setBounds(5, 256, 249, 137);

		Label lblObligations = new Label(container, SWT.NONE);
		lblObligations.setBounds(5, 234, 249, 15);
		lblObligations.setText("Obligations:");

		Button addObligation = new Button(container, SWT.NONE);
		addObligation.setBounds(290, 295, 110, 25);
		addObligation.setText(">>");

		licenseObligations = new List(container, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		licenseObligations.setBounds(438, 256, 245, 137);

		Button removeObligation = new Button(container, SWT.NONE);
		removeObligation.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				for (int selectionIndex : licenseObligations.getSelectionIndices()) {
					obligationList.add(licenseObligations.getItem(selectionIndex));
					licenseObligations.remove(selectionIndex);
				}
			}
		});
		removeObligation.setBounds(290, 326, 110, 25);
		removeObligation.setText("<<");

		addObligation.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				for (int selectionIndex : obligationList.getSelectionIndices()) {
					licenseObligations.add(obligationList.getItem(selectionIndex));
					obligationList.remove(selectionIndex);
				}
			}
		});

		Label lblAdditionalConditions = new Label(container, SWT.NONE);
		lblAdditionalConditions.setBounds(5, 413, 678, 15);
		lblAdditionalConditions.setText("Additional Conditions:");

		hasLimitedLiability = new Button(container, SWT.CHECK);
		hasLimitedLiability.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		hasLimitedLiability.setBounds(5, 434, 678, 16);
		hasLimitedLiability.setText("LimitedLiability");

		hasProvidedWithoutWarranty = new Button(container, SWT.CHECK);
		hasProvidedWithoutWarranty.setText("ProvideWithoutWarranty");
		hasProvidedWithoutWarranty.setBounds(5, 456, 678, 16);

		Label lblLicense = new Label(container, SWT.NONE);
		lblLicense.setBounds(5, 10, 55, 15);
		lblLicense.setText("License:");

		licenseTitle = new CCombo(container, SWT.BORDER);
		licenseTitle.setItems(new String[] { "AAL", "ADSL", "AFL-1.1", "AFL-1.2", "AFL-2.0", "AFL-2.1", "AFL-3.0",
				"AGPL-1.0", "AGPL-3.0", "AMDPLPA", "AML", "AMPAS", "ANTLR-PD", "APAFML", "APL-1.0", "APSL-1.0",
				"APSL-1.1", "APSL-1.2", "APSL-2.0", "Abstyles", "Adobe-2006", "Adobe-Glyph", "Afmparse", "Aladdin",
				"Apache-1.0", "Apache-1.1", "Apache-2.0", "Artistic-1.0", "Artistic-1.0-Perl", "Artistic-1.0-cl8",
				"Artistic-2.0", "BSD-2-Clause", "BSD-2-Clause-FreeBSD", "BSD-2-Clause-NetBSD", "BSD-3-Clause",
				"BSD-3-Clause-Attribution", "BSD-3-Clause-Clear", "BSD-3-Clause-LBNL", "BSD-4-Clause",
				"BSD-4-Clause-UC", "BSD-Protection", "BSL-1.0", "Bahyph", "Barr", "Beerware", "BitTorrent-1.0",
				"BitTorrent-1.1", "Borceux", "CATOSL-1.1", "CC-BY-1.0", "CC-BY-2.", "CC-BY-2.5", "CC-BY-3.0",
				"CC-BY-4.0", "CC-BY-NC-1.0", "CC-BY-NC-2.0", "CC-BY-NC-2.5", "CC-BY-NC-3.0", "CC-BY-NC-4.0",
				"CC-BY-NC-ND-1.0", "CC-BY-NC-ND-2.0", "CC-BY-NC-ND-2.5", "CC-BY-NC-ND-3.0", "CC-BY-NC-ND-4.0",
				"CC-BY-NC-SA-1.0", "CC-BY-NC-SA-2.0", "CC-BY-NC-SA-2.5", "CC-BY-NC-SA-3.0", "CC-BY-NC-SA-4.0",
				"CC-BY-ND-1.0", "CC-BY-ND-2.0", "CC-BY-ND-2.5", "CC-BY-ND-3.0", "CC-BY-ND-4.0", "CC-BY-SA-1.0",
				"CC-BY-SA-2.0", "CC-BY-SA-2.5", "CC-BY-SA-3.0", "CC-BY-SA-4.0", "CC0-1.0", "CDDL-1.0", "CDDL-1.1",
				"CECILL-1.0", "CECILL-1.1", "CECILL-2.0", "CECILL-B", "CECILL-C", "CNRI-Python",
				"CNRI-Python-GPL-Compatible", "CPAL-1.0", "CPL-1.0", "CPOL-1.02", "CUA-OPL-1.0", "Caldera",
				"ClArtistic", "Condor-1.1", "Crossword", "Cube", "D-FSL-1.0", "DOC", "DSDP", "Dotseqn", "ECL-1.0",
				"ECL-2.0", "EFL-1.0", "EFL-2.0", "EPL-1.0", "EUDatagrid", "EUPL-1.0", "EUPL-1.1", "Entessa",
				"ErlPL-1.1", "Eurosym", "FSFUL", "FSFULLR", "FTL", "Fair", "Frameworx-1.0", "FreeImage", "GFDL-1.1",
				"GFDL-1.2", "GFDL-1.3", "GL2PS", "GPL-1.0", "GPL-1.0+", "GPL-2.0", "GPL-2.0+",
				"GPL-2.0-with-GCC-exception", "GPL-2.0-with-autoconf-exception", "GPL-2.0-with-bison-exception",
				"GPL-2.0-with-classpath-exception", "GPL-2.0-with-font-exception", "GPL-3.0", "GPL-3.0+",
				"GPL-3.0-with-GCC-exception", "GPL-3.0-with-autoconf-exception", "Giftware", "Glide", "Glulxe", "HPND",
				"HaskellReport", "IBM-pibs", "ICU", "IJG", "IPA", "IPL-1.0", "ISC", "ImageMagick", "Imlib2", "Intel",
				"Intel-ACPI", "JSON", "JasPer-2.0", "LGPL-2.0", "LGPL-2.0+", "LGPL-2.1", "LGPL-2.1+", "LGPL-3.0",
				"LGPL-3.0+", "LPL-1.0", "LPL-1.02", "LPPL-1.0", "LPPL-1.1", "LPPL-1.2", "LPPL-1.3a", "LPPL-1.3c",
				"Latex2e", "Leptonica", "Libpng", "MIT", "MIT-CMU", "MIT-advertising", "MIT-enna", "MIT-feh", "MITNFA",
				"MPL-1.0", "MPL-1.1", "MPL-2.0", "MPL-2.0-no-copyleft-exception", "MS-PL", "MS-RL", "MTLL", "MakeIndex",
				"MirOS", "Motosoto", "Multics", "Mup", "NASA-1.3", "NBPL-1.0", "NCSA", "NGPL", "NLPL", "NOSL",
				"NPL-1.0", "NPL-1.1", "NPOSL-3.0", "NRL", "NTP", "Naumen", "NetCDF", "Newsletr", "Nokia", "Noweb",
				"Nunit", "OCLC-2.0", "ODbL-1.0", "OFL-1.0", "OFL-1.1", "OGTSL", "OLDAP-1.1", "OLDAP-1.2", "OLDAP-1.3",
				"OLDAP-1.4", "OLDAP-2.0", "OLDAP-2.0.1", "OLDAP-2.1", "OLDAP-2.2", "OLDAP-2.2.1", "OLDAP-2.2.2",
				"OLDAP-2.3", "OLDAP-2.4", "OLDAP-2.5", "OLDAP-2.6", "OLDAP-2.7", "OLDAP-2.8", "OML", "OPL-1.0",
				"OSL-1.0", "OSL-1.1", "OSL-2.0", "OSL-2.1", "OSL-3.0", "OpenSSL", "PDDL-1.0", "PHP-3.0", "PHP-3.01",
				"Plexus", "PostgreSQL", "Python-2.0", "QPL-1.0", "Qhull", "RHeCos-1.1", "RPL-1.1", "RPL-1.5",
				"RPSL-1.0", "RSCPL", "Rdisc", "Ruby", "SAX-PD", "SCEA", "SGI-B-1.0", "SGI-B-1.1", "SGI-B-2.0", "SISSL",
				"SISSL-1.2", "SMLNJ", "SNIA", "SPL-1.0", "SWL", "Saxpath", "SimPL-2.0", "Sleepycat", "StandardML-NJ",
				"SugarCRM-1.1.3", "TCL", "TMate", "TORQUE-1.1", "TOSL", "Unicode-TOU", "Unlicense", "VOSTROM",
				"VSL-1.0", "Vim", "W3C", "W3C-19980720", "WTFPL", "WXwindows", "Watcom-1.0", "Wsuipa", "X11",
				"XFree86-1.1", "XSkat", "Xerox", "Xnet", "YPL-1.0", "YPL-1.1", "ZPL-1.1", "ZPL-2.0", "ZPL-2.1", "Zed",
				"Zend-2.0", "Zimbra-1.3", "Zimbra-1.4", "Zlib", "bzip2-1.0.5", "bzip2-1.0.6", "diffmark", "dvipdfm",
				"eCos-2.0", "eGenix", "gSOAP-1.3b", "gnuplot", "iMatix", "libtiff", "mpich2", "psfrag", "psutils",
				"xinetd", "xpp", "zlib-acknowledgement" });
		licenseTitle.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (licenseTitle.getSelectionIndex() >= 0) {
					currentPage.setPageComplete(true);
				}
			}
		});
		licenseTitle.setText("Select a license");
		licenseTitle.setBounds(66, 10, 617, 21);
	}
}