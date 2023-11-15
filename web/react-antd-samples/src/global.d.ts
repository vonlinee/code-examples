import * as React from "react";

declare interface SvgrComponent
  extends React.StatelessComponent<React.SVGAttributes<SVGElement>> {}

declare module "*.svg" {
  const content: SvgrComponent;
  export default content;
}
