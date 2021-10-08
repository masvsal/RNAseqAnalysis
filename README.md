##Personal Project

###Samuel Salitra - 10/1/21

Questions to Answer:
- **What will the Application Do?** </br>
It will be a suite of everyday bioinformatic tools that a scientist might use. This would involve choosing from a list of possible experiments and performing simple analysis on the *RNAseq* data contained within it. 
- **Who will use it?** </br>
Bioinformaticians might use it in their day-to-day work to automate tedious tasks.
- **Why is this project of interest to you?** </br>
I would like to work in bioinformatics so I want to understand the basic workflow of a typical bioinformatic program.

Use at least two of:
- bold/italic
- title/subtitle
- bulleted list

An example of text with **bold** and *italic* fonts.  Note that the IntelliJ markdown previewer doesn't seem to render 
the bold and italic fonts correctly but they will appear correctly on GitHub.

####User Stories

- As a user, I want to add or remove an experiment to/from the directory of my experiments
- As a user, I want to be able to change the description of an experiment
- As a user, I want to be able to add or remove a data file to an experiment
- As a user, I want to set a significance threhsold and find significant differences between two data files contained within one of my experiments.
####TODO

- abstract qPCR and RNAseq data (what are the fields, what operations to do to them)
  
  - convert tabulated data to FC
  - extract list of genes that exhibit significant FC
  - extract list of genes that agree on significant FC in both qPCR and RNAseq
  - **what does qPCR and RNAseq output look like?**
- create classes for them
- write tests
- implement methods in these classes
- implement user input

  - choose the significance threshold


