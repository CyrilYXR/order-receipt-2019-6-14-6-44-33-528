package org.katas.refactoring;

/**
 * OrderReceipt prints the details of order including customer name, address, description, quantity,
 * price and amount. It also calculates the sales tax @ 10% and prints as part
 * of order. It computes the total order amount (amount of individual lineItems +
 * total sales tax) and prints it.
 */
public class OrderReceipt {
    public static final double TAX_RATE = .10;
    private Order order;

    public OrderReceipt(Order order) {
        this.order = order;
    }

    public String printReceipt() {
        StringBuilder output = new StringBuilder();
        printHeaders(output);
        appendNameAndAddress(output);

        // prints lineItems
        double totSalesTx = 0d;
        double tot = 0d;
        for (LineItem lineItem : order.getLineItems()) {
            appendDetail(output, lineItem);

            // calculate sales tax @ rate of 10%
            double salesTax = lineItem.totalAmount() * TAX_RATE;
            totSalesTx += salesTax;

            // calculate total amount of lineItem = price * quantity + 10 % sales tax
            tot = getTot(tot, lineItem, salesTax);
        }
        appendSalesTax(output, totSalesTx);

        appendTotalAmount(output, tot);
        return output.toString();
    }

    private double getTot(double tot, LineItem lineItem, double salesTax) {
        tot += lineItem.totalAmount() + salesTax;
        return tot;
    }

    private StringBuilder appendTotalAmount(StringBuilder output, double tot) {
        return output.append("Total Amount").append('\t').append(tot);
    }

    private void appendSalesTax(StringBuilder output, double totSalesTx) {
        // prints the state tax
        output.append("Sales Tax").append('\t').append(totSalesTx);
    }

    private void appendDetail(StringBuilder output, LineItem lineItem) {
        output.append(lineItem.getDescription());
        output.append('\t');
        output.append(lineItem.getPrice());
        output.append('\t');
        output.append(lineItem.getQuantity());
        output.append('\t');
        output.append(lineItem.totalAmount());
        output.append('\n');
    }

    private void appendNameAndAddress(StringBuilder output) {
        output.append(order.getCustomerName());
        output.append(order.getCustomerAddress());
    }

    private void printHeaders(StringBuilder output) {
        output.append("======Printing Orders======\n");
    }
}