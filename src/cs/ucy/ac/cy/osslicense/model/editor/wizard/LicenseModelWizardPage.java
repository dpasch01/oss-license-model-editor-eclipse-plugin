package cs.ucy.ac.cy.osslicense.model.editor.wizard;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class LicenseModelWizardPage extends WizardPage {

	private Text text1;
	private Composite container;

	protected LicenseModelWizardPage(String pageName) {
		super(pageName);
		setTitle("First Page");
		setDescription("Fake Wizard: First page");
	}

	@Override
	public void createControl(Composite parent) {
		container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 2;

		Label label1 = new Label(container, SWT.NONE);
		label1.setText("Put a value here.");

		text1 = new Text(container, SWT.BORDER | SWT.SINGLE);
		text1.setText("");

		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		text1.setLayoutData(gd);

		setControl(container);
		setPageComplete(false);
	}

	public String getText1() {
		return text1.getText();
	}

}